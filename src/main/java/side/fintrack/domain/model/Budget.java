package side.fintrack.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Entity
@NoArgsConstructor
@Getter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    //    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
//    @NonNull
    private YearMonth month;

    @Column(nullable = false)
//    @NonNull
    private BigDecimal totalBudget;

    @Column(nullable = false)
//    @NonNull
    private LocalDateTime createdAt;

    @Builder
    public Budget(User user, YearMonth month, BigDecimal totalBudget, LocalDateTime createdAt) {
        this.user = user;
        this.month = month;
        this.totalBudget = totalBudget;
        this.createdAt = createdAt;
    }

    public boolean isExceeded(BigDecimal totalExpense) {
        return totalExpense.compareTo(this.totalBudget) > 0;
    }

}
