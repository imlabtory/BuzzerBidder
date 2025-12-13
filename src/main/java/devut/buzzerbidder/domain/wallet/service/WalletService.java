package devut.buzzerbidder.domain.wallet.service;

import devut.buzzerbidder.domain.user.entity.User;
import devut.buzzerbidder.domain.wallet.entity.Wallet;
import devut.buzzerbidder.domain.wallet.entity.WalletHistory;
import devut.buzzerbidder.domain.wallet.enums.WalletTransactionType;
import devut.buzzerbidder.domain.wallet.repository.WalletHistoryRepository;
import devut.buzzerbidder.domain.wallet.repository.WalletRepository;
import devut.buzzerbidder.global.exeption.BusinessException;
import devut.buzzerbidder.global.exeption.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletHistoryRepository walletHistoryRepository;

    @Transactional(readOnly = true)
    public Wallet findByUserIdOrThrow(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND));
    }

    public void createWallet(User user) {
        if(walletRepository.existsByUserId(user.getId())) {
            throw new BusinessException(ErrorCode.WALLET_ALREADY_EXISTS);
        }

        Wallet wallet = Wallet.builder()
                .user(user)
                .bizz(0L)
                .build();

        walletRepository.save(wallet);
    }

    @Transactional(readOnly = true)
    public Long getBizzBalance(User user) {
        Wallet wallet = findByUserIdOrThrow(user.getId());
        return wallet.getBizz();
    }

    public void chargeBizz(User user, Long amount) {
        Wallet wallet = findByUserIdOrThrow(user.getId());

        updateBizzWithHistory(wallet, user, amount, WalletTransactionType.CHARGE);
    }

    public void transferBizz(User fromUser, User toUser, Long amount) {
        if (fromUser.getId().equals(toUser.getId())) {
            throw new BusinessException(ErrorCode.INVALID_TRANSFER);
        }

        Wallet fromWallet = findByUserIdOrThrow(fromUser.getId());
        Wallet toWallet = findByUserIdOrThrow(toUser.getId());

        updateBizzWithHistory(fromWallet, fromUser, amount, WalletTransactionType.PAY_TO_USER);
        updateBizzWithHistory(toWallet, toUser, amount, WalletTransactionType.RECEIVE_FROM_USER);
    }

    private void updateBizzWithHistory(Wallet wallet, User user, Long amount, WalletTransactionType type) {
        if (amount == null || amount <= 0) throw new BusinessException(ErrorCode.INVALID_WALLET_AMOUNT);

        Long balanceBefore = wallet.getBizz();
        if (type.isIncrease()) {
            wallet.increaseBizz(amount);
        } else {
            wallet.decreaseBizz(amount);
        }
        Long balanceAfter = wallet.getBizz();

        recordWalletHistory(user, amount, type, balanceBefore, balanceAfter);
    }

    private void recordWalletHistory(User user, Long amount, WalletTransactionType type, Long balanceBefore, Long balanceAfter) {
        WalletHistory walletHistory = WalletHistory.builder()
                .user(user)
                .amount(amount)
                .type(type)
                .bizzBalanceBefore(balanceBefore)
                .bizzBalanceAfter(balanceAfter)
                .build();

        walletHistoryRepository.save(walletHistory);
    }

}
