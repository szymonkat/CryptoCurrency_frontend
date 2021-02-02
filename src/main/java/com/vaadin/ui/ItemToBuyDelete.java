package com.vaadin.ui;

import com.vaadin.client.ItemToBuyClient;
import com.vaadin.domain.LongVal;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import java.util.List;


@Route(value = "itemToBuyDelete", layout = MainLayout.class)
public class ItemToBuyDelete extends FormLayout {

    @PropertyId("idValue")
    ComboBox<Long> idValue = new ComboBox<>("Select Item to Buy You want to delete");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<LongVal> binder = new BeanValidationBinder<>(LongVal.class);
    private LongVal longVal;


    public ItemToBuyDelete(List<Long> itemToBuyLongList, ItemToBuyClient itemToBuyClient) {
        addClassName("item-to-buy-delete");
        binder.bindInstanceFields(this);
        idValue.setItems(itemToBuyLongList);
        idValue.setItemLabelGenerator(Long -> itemToBuyClient.getItemToBuyById(Long).toString());
        add(
                idValue,
                createButtonsLayout()
        );
    }

    public void setLongVal(LongVal longVal) {
        this.longVal = longVal;
        binder.readBean(longVal);
    }

    private Component createButtonsLayout() {
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> deleteAction());
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(delete, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(longVal);
            fireEvent(new SaveEvent(this, longVal));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void deleteAction() {
        validateAndSave();
        fireEvent(new DeleteEvent(this, longVal));
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class ItemToBuyDeleteEvent extends ComponentEvent<ItemToBuyDelete> {
        private final LongVal longVal;

        protected ItemToBuyDeleteEvent(ItemToBuyDelete source, LongVal longVal) {
            super(source, false);
            this.longVal = longVal;
        }

        public LongVal getLongVal() {
            return longVal;
        }
    }

    public static class SaveEvent extends ItemToBuyDeleteEvent {
        SaveEvent(ItemToBuyDelete source, LongVal longVal) {
            super(source, longVal);
        }
    }

    public static class DeleteEvent extends ItemToBuyDeleteEvent {
        DeleteEvent(ItemToBuyDelete source, LongVal longVal) {
            super(source, longVal);
        }
    }

    public static class CloseEvent extends ItemToBuyDeleteEvent {
        CloseEvent(ItemToBuyDelete source) {
            super(source, null);
        }
    }
}
