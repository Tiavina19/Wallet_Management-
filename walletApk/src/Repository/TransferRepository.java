package Repository;

import Models.Transfer;

import java.util.ArrayList;
import java.util.List;

public class TransferRepository implements TransferCrudOperations {

    private List<Transfer> transfers = new ArrayList<>();

    @Override
    public void addTransfer(Transfer transfer) {
        transfers.add(transfer);
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return new ArrayList<>(transfers);
    }

    @Override
    public void deleteTransfer(String transferId) {
        transfers.removeIf(transfer -> transfer.getId().equals(transferId));
    }

    @Override
    public void updateTransfer(Transfer transfer) {
        int index = indexOfTransfer(transfer.getId());
        if (index != -1) {
            transfers.set(index, transfer);
        }
    }

    private int indexOfTransfer(String transferId) {
        for (int i = 0; i < transfers.size(); i++) {
            if (transfers.get(i).getId().equals(transferId)) {
                return i;
            }
        }
        return -1;
    }
}
