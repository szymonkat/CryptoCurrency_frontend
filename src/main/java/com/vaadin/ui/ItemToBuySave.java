package com.vaadin.ui;

import com.vaadin.domain.ExchangePortal;
import com.vaadin.domain.ItemToBuy;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import java.util.List;

@Route(value = "itemToBuySave", layout = MainLayout.class)
public class ItemToBuySave extends FormLayout {

    ComboBox<ExchangePortal> exchangePortal = new ComboBox<>("ExchangePortal");
    NumberField quantityToBuy = new NumberField("Quantity to buy");

    Button save = new Button("Save");
    Button close = new Button("Cancel");

    Binder<ItemToBuy> binder = new BeanValidationBinder<>(ItemToBuy.class);
    private ItemToBuy itemToBuy;


    public ItemToBuySave(List<ExchangePortal> exchangePortalList) {
        addClassName("item-to-buy-save");
        binder.bindInstanceFields(this);
        exchangePortal.setItems(exchangePortalList);
        exchangePortal.setItemLabelGenerator(ExchangePortal::toString);

        add(
                exchangePortal,
                quantityToBuy,
                createButtonsLayout()
        );
    }

    public void setItemToBuy(ItemToBuy itemToBuy) {
        this.itemToBuy = itemToBuy;
        binder.readBean(itemToBuy);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(itemToBuy);
            fireEvent(new SaveEvent(this, itemToBuy));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ItemToBuySaveEvent extends ComponentEvent<ItemToBuySave> {
        private ItemToBuy itemToBuy;

        protected ItemToBuySaveEvent(ItemToBuySave source, ItemToBuy itemToBuy) {
            super(source, false);
            this.itemToBuy = itemToBuy;
        }

        public ItemToBuy getItemToBuy() {
            return itemToBuy;
        }
    }

    public static class SaveEvent extends ItemToBuySaveEvent {
        SaveEvent(ItemToBuySave source, ItemToBuy itemToBuy) {
            super(source, itemToBuy);
        }
    }


    public static class CloseEvent extends ItemToBuySaveEvent {
        CloseEvent(ItemToBuySave source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
