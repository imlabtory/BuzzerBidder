package devut.buzzerbidder.domain.wallet.repository;

import devut.buzzerbidder.domain.wallet.entity.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletHistoryRepository extends JpaRepository<WalletHistory, Long> {

}
