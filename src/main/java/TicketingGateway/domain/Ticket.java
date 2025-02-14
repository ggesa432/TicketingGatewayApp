package TicketingGateway.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GesangZeren
 * @project  TicketingGateway
 * @date  11/12/2024
 *
 */
@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String department;
    private String priority;
    private String type;
    private LocalDate dueDate;
    private String project;
    private String description;
    private Double amount;
    private String status = "Pending";
    private String progress;
    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.progress = "In Progress";
    }

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
    private LocalDateTime reopenedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    private List<TicketHistory> history;

    private String assignedTo;
    private String reopenedBy;

    @ElementCollection
    @CollectionTable(name = "ticket_images", joinColumns = @JoinColumn(name = "ticket_id"))
    @Column(name = "image_path")
    private List<String> imagePaths = new ArrayList<>();

    @Transient
    private boolean reopenable;

    public boolean isReopenable() {
        return reopenable;
    }

    public void setReopenable(boolean reopenable) {
        this.reopenable = reopenable;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", department='" + department + '\'' +
                ", priority='" + priority + '\'' +
                ", type='" + type + '\'' +
                ", dueDate=" + dueDate +
                ", project='" + project + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", progress='" + progress + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", createdAt=" + createdAt +
                ", closedAt=" + closedAt +
                ", history='" + history + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public LocalDateTime getReopenedAt() {
        return reopenedAt;
    }

    public void setReopenedAt(LocalDateTime reopenedAt) {
        this.reopenedAt = reopenedAt;
    }

    public List<TicketHistory> getHistory() {
        return history;
    }

    public void setHistory(List<TicketHistory> history) {
        this.history = history;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getReopenedBy() {
        return reopenedBy;
    }

    public void setReopenedBy(String reopenedBy) {
        this.reopenedBy = reopenedBy;
    }
}
