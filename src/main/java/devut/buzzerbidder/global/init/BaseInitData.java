package devut.buzzerbidder.global.init;

import devut.buzzerbidder.domain.deal.entity.LiveDeal;
import devut.buzzerbidder.domain.deal.enums.DealStatus;
import devut.buzzerbidder.domain.deal.repository.LiveDealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;


@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    @Autowired
    @Lazy
    private BaseInitData self;

    private final LiveDealRepository liveDealRepository;

    @Bean
    ApplicationRunner initDataRunner() {
        return args -> {
            self.userInitData();
            self.liveDealInitData();
        };
    }

    @Transactional
    public void userInitData() {
        // TODO: 유저 초기 데이터 생성 로직 작성
    }
    @Transactional
    public void liveDealInitData() {
        if(liveDealRepository.count() > 0) {
            return;
        }
        LiveDeal liveDeal = LiveDeal.builder().
                itemId(1).
                buyerId(1).
                winningPrice(10000).
                status(DealStatus.PENDING).
                trackingNumber(null).
                carrier(null).
                build();
        liveDealRepository.save(liveDeal);
    }

}