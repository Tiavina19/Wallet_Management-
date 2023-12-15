package Repository;

import Models.BalanceHistory;

import java.util.ArrayList;
import java.util.List;

public class BalanceHistoryRepository implements BalanceHistoryCrudOperations {

    private List<BalanceHistory> balanceHistories = new ArrayList<>();

    @Override
    public void addBalanceHistory(BalanceHistory balanceHistory) {
        balanceHistories.add(balanceHistory);
    }

    @Override
    public List<BalanceHistory> getAllBalanceHistory() {
        return new ArrayList<>(balanceHistories);
    }

    @Override
    public void deleteBalanceHistory(String balanceHistoryId) {
        balanceHistories.removeIf(history -> history.getId().equals(balanceHistoryId));
    }

    @Override
    public void updateBalanceHistory(BalanceHistory balanceHistory) {
        int index = indexOfBalanceHistory(balanceHistory.getId());
        if (index != -1) {
            balanceHistories.set(index, balanceHistory);
        }
    }

    // Ajouter des méthodes spécifiques à l'historique des soldes si nécessaire...

    private int indexOfBalanceHistory(String balanceHistoryId) {
        for (int i = 0; i < balanceHistories.size(); i++) {
            if (balanceHistories.get(i).getId().equals(balanceHistoryId)) {
                return i;
            }
        }
        return -1;
    }
}
