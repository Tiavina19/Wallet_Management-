package Services;

import Models.Transfer;
import Repository.TransferCrudOperations;

import java.util.List;

public class TransferService {
    private TransferCrudOperations transferCrudOperations;

    public TransferService(TransferCrudOperations transferCrudOperations) {
        this.transferCrudOperations = transferCrudOperations;
    }

    public void addTransfer(Transfer transfer) {
        transferCrudOperations.addTransfer(transfer);
    }

    public List<Transfer> getAllTransfers() {
        return transferCrudOperations.getAllTransfers();
    }

    public void deleteTransfer(String transferId) {
        transferCrudOperations.deleteTransfer(transferId);
    }

    public void updateTransfer(Transfer transfer) {
        transferCrudOperations.updateTransfer(transfer);
    }
}
