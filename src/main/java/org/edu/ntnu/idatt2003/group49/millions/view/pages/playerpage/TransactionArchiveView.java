package org.edu.ntnu.idatt2003.group49.millions.view.pages.playerpage;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.edu.ntnu.idatt2003.group49.millions.controller.PlayerController;
import org.edu.ntnu.idatt2003.group49.millions.model.transaction.Transaction;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.TableSelectionModel;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.TransactionTable;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsTable.factory.TransactionsColumnFactory;
import org.edu.ntnu.idatt2003.group49.millions.view.MillionsView;

import java.util.Objects;

public class TransactionArchiveView extends MillionsView {
  private final PlayerController playerController;
  private final TableSelectionModel<Transaction> selectionModel;

  public TransactionArchiveView(PlayerController playerController) {
    this.playerController = Objects.requireNonNull(playerController, "playerController cannot be null");
    this.selectionModel = new TableSelectionModel<>();

    getChildren().add(build());
  }

  @Override
  protected Pane build() {
    VBox archive = new VBox();
    archive.getStyleClass().add("archive-section");
    archive.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    TransactionTable transactionTable = new TransactionTable(
        new TransactionsColumnFactory(),
        selectionModel
    );

    archive.getChildren().add(createTitle());

    if (playerController.getTransactions().isEmpty()) {
      Pane emptyArchiveMessage = createEmptyArchiveMessage();
      VBox.setVgrow(emptyArchiveMessage, Priority.ALWAYS);
      archive.getChildren().add(emptyArchiveMessage);
    } else {
      transactionTable.setItems(playerController.getTransactions());
      VBox.setVgrow(transactionTable, Priority.ALWAYS);
      archive.getChildren().add(transactionTable);
    }

    return archive;
  }

  private Label createTitle() {
    Label title = new Label("Transaction Archive");
    title.getStyleClass().add("section-title");
    title.setMaxWidth(Double.MAX_VALUE);
    title.setAlignment(Pos.CENTER);
    return title;
  }

  private Pane createEmptyArchiveMessage() {
    VBox container = new VBox();
    container.getStyleClass().add("empty-archive-container");
    container.setAlignment(Pos.CENTER);
    container.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    Label message = new Label("No transactions yet");
    message.getStyleClass().add("empty-archive-message");

    container.getChildren().add(message);
    return container;
  }

}
