package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    private Logic logic;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList} and {@code ObjectProperty}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObjectProperty<Person> selectedPerson) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

    }

    /**
     * Scrolls the {@code PersonListPanel} to the given {@code Person} and selects them.
     * The scroll and selection are performed on the JavaFX Application Thread via
     * {@code Platform.runLater} to ensure UI updates are safe regardless of the calling thread.
     *
     * @param person the {@code Person} to scroll to and select
     */
    public void scrollToAndSelect(Person person) {
        Platform.runLater(() -> {
            if (person == null) {
                personListView.getSelectionModel().clearSelection();
                return;
            }

            // it cannot be declared as just index as it must be effectively final
            // for the lines `event -> personListView.scrollTo(index)`
            int tempIndex = -1;

            for (int i = 0; i < personListView.getItems().size(); i++) {
                Person p = personListView.getItems().get(i);
                if (p.exactEquals(person)) {
                    tempIndex = i;
                    break;
                }
            }
            if (tempIndex < 0) {
                return;
            }

            int index = tempIndex;
            personListView.getSelectionModel().select(index);

            // Small delay to allow JavaFX layout pass to complete before scrolling
            PauseTransition pause = new PauseTransition(Duration.millis(70));
            pause.setOnFinished(event -> personListView.scrollTo(index));
            pause.play();
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
