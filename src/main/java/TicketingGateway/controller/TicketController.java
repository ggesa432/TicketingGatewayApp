package TicketingGateway.controller;

import TicketingGateway.domain.Ticket;
import TicketingGateway.domain.TicketHistory;
import TicketingGateway.domain.User;
import TicketingGateway.repository.TicketHistoryRepository;
import TicketingGateway.repository.TicketRepository;
import TicketingGateway.repository.UserRepository;
import TicketingGateway.service.PdfGeneratorService;
import TicketingGateway.service.TicketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 11/11/2024
 */
@Controller
@RestController
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketHistoryRepository ticketHistoryRepository;
    @Autowired
    private TicketNotificationService ticketNotificationService;
    @Autowired
    private PdfGeneratorService pdfGeneratorService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/ticket")
    public ModelAndView homePage(Model model) {
        model.addAttribute("message", "Welcome to the Ticketing Gateway Home Page");
        return new ModelAndView("ticket");
    }
    @PostMapping("/ticket")
    public ModelAndView submitTicket(@RequestParam("department") String department,
                                     @RequestParam("priority") String priority,
                                     @RequestParam("type") String type,
                                     @RequestParam("due-date") LocalDate dueDate,
                                     @RequestParam("project") String project,
                                     @RequestParam("description") String description,
                                     @RequestParam("amount") String amount,
                                     @RequestParam("email") String email,
                                     @RequestParam("images") MultipartFile[] images,
                                     Model model
                                     ) {

        Ticket ticket = new Ticket();
        ticket.setDepartment(department);
        ticket.setPriority(priority);
        ticket.setType(type);
        ticket.setDueDate(dueDate);
        ticket.setProject(project);
        ticket.setDescription(description);
        ticket.setAmount(Double.parseDouble(amount));

        List<String> imagePaths = new ArrayList<>();
        // Save uploaded images
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try {
                    String uploadDir = "uploads/tickets/";
                    String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                    Path uploadPath = Paths.get(uploadDir + fileName);
                    Files.createDirectories(uploadPath.getParent());
                    Files.write(uploadPath, image.getBytes());
                    imagePaths.add(fileName);
                } catch (IOException e) {
                    System.err.println("Failed to upload image: " + e.getMessage());
                    model.addAttribute("error", "Failed to upload one or more images.");
                }
            }
        }

        ticket.setImagePaths(imagePaths);
        ticketRepository.save(ticket);

        String subject = "Ticket Raised Successfully";

        String body = String.format("Dear User,\n\nYour ticket has been raised successfully.\n\nDetails:\nDepartment: %s\nPriority: %s\nType: %s\nDue Date: %s\nProject: %s\nDescription: %s\nAmount: %s",
                department, priority, type, dueDate, project, description, amount);


        try {
            ticketNotificationService.sendTicketNotification(email, subject, body);
            model.addAttribute("message", "Ticket submitted successfully and email notification sent.");
        } catch (Exception e) {
            System.err.println("Failed to send email notification: " + e.getMessage());
            model.addAttribute("message", "Ticket submitted successfully, but email notification failed.");
        }

        List<Ticket> allTickets = ticketRepository.findAll();
        model.addAttribute("allTickets", allTickets);

        return new ModelAndView("ticket-history");

    }
    @GetMapping("/ticket-history")
    public ModelAndView showTicketHistory() {
        ModelAndView modelAndView = new ModelAndView("ticket-history"); // Specify the view name

        List<Ticket> tickets = ticketRepository.findAll(); // Fetch all tickets

        LocalDateTime now = LocalDateTime.now();

        // Calculate the reopenable flag for each ticket
        for (Ticket ticket : tickets) {
            if ("Closed".equals(ticket.getProgress()) && ticket.getClosedAt() != null) {
                boolean isClosedRecently = ticket.getClosedAt().isAfter(now.minusDays(7));
                ticket.setReopenable(isClosedRecently); // Set reopenable flag
            } else {
                ticket.setReopenable(false); // Default to false
            }

        }

        // Add the tickets list to the model
        modelAndView.addObject("allTickets", tickets);

        return modelAndView;
    }

    @GetMapping("/approve-tickets")
    public ModelAndView showApproveTicketsPage(Model model) {
        List<Ticket> allTickets = ticketRepository.findAll();
        model.addAttribute("allTickets", allTickets);
        return new ModelAndView("approve-tickets");
    }

    @GetMapping("/ticket-details/{id}")
    public ModelAndView viewTicketDetails(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("ticket-details");

        // Fetch the ticket details
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            Ticket ticketDetails = ticket.get();
            modelAndView.addObject("ticket", ticketDetails);

            // Fetch the ticket history
            List<TicketHistory> history = ticketHistoryRepository.findByTicketId(id);
            modelAndView.addObject("ticketHistory", history);
        } else {
            modelAndView.setViewName("redirect:/approve-tickets");
            modelAndView.addObject("error", "Ticket not found");
        }

        return modelAndView;
    }


    @PostMapping("/approve-ticket/{id}")
    public ModelAndView approveTicket(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            Ticket ticketToUpdate = ticket.get();
            ticketToUpdate.setStatus("Approved");
            ticketRepository.save(ticketToUpdate);
        }
        return new ModelAndView("redirect:/approve-tickets");
    }
    @PostMapping("/decline-ticket/{id}")
    public ModelAndView declineTicket(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            Ticket ticketToUpdate = ticket.get();
            ticketToUpdate.setStatus("Declined");
            ticketRepository.save(ticketToUpdate);
        }
        return new ModelAndView("redirect:/approve-tickets") ;
    }



    @PostMapping("/delete-ticket/{id}")
    public ModelAndView deleteTicket(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Ticket deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Ticket not found!");
        }
        return new ModelAndView("redirect:/approve-tickets") ;
    }

    /*@PostMapping("/mark-in-progress/{id}")
    public ModelAndView markInProgress(@PathVariable Long id, Model model) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            Ticket ticketToUpdate = ticket.get();
            ticketToUpdate.setStatus("In Progress");
            ticketRepository.save(ticketToUpdate);
            model.addAttribute("message", "Ticket marked as In Progress.");
        } else {
            model.addAttribute("error", "Ticket not found.");
        }
        return new ModelAndView("ticket-history");
    }*/

    @PostMapping("/mark-closed/{id}")
    public ModelAndView markClosed(@PathVariable Long id, Model model) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            Ticket ticketToUpdate = ticket.get();
            ticketToUpdate.setProgress("Closed");
            ticketToUpdate.setClosedAt(LocalDateTime.now());
            ticketRepository.save(ticketToUpdate);

            String pdfPath = pdfGeneratorService.generatePdfSummary(ticketToUpdate);


            String subject = "Ticket Marked as Closed";
            String body = String.format("Dear User,\n\nYour ticket with ID: %d has been marked as Closed.\n\nDetails:\n" +
                            "Department: %s\nPriority: %s\nType: %s\nDue Date: %s\nProject: %s\nDescription: %s\nAmount: %s",
                    ticketToUpdate.getId(), ticketToUpdate.getDepartment(), ticketToUpdate.getPriority(),
                    ticketToUpdate.getType(), ticketToUpdate.getDueDate(), ticketToUpdate.getProject(),
                    ticketToUpdate.getDescription(), ticketToUpdate.getAmount());

            try {
                ticketNotificationService.sendEmailNotificationWithAttachment(
                        "gesangsession66@gmail.com",
                        subject,
                        body,
                        pdfPath
                );
                model.addAttribute("message", "Ticket marked as Closed. Email notification sent.");
            } catch (Exception e) {
                System.err.println("Failed to send email notification: " + e.getMessage());
                model.addAttribute("error", "Ticket marked as Closed, but email notification failed.");
            }
        } else {
            model.addAttribute("error", "Ticket not found.");
        }
        return new ModelAndView("redirect:/ticket-history");
    }

    @PostMapping("/tickets/reopen/{id}")
    public ModelAndView reopenTicket(@PathVariable Long id, Principal principal) {
        String userName = principal.getName(); // Get logged-in user's username
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        ticketNotificationService.reopenTicket(id, user);

        return new ModelAndView("redirect:/ticket-history")
                .addObject("message", "Ticket " + id + " has been successfully reopened.");
    }


    @GetMapping("/tickets/{ticketId}/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) {
        String filePath = "uploads/tickets/" + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("/tickets/download/{ticketId}/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName) {
        String filePath = "uploads/tickets/" + fileName;
        System.out.println("File Path: " + filePath);
        File file = new File(filePath);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }


}
