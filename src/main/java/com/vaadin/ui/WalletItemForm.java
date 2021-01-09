package com.vaadin.ui;

import com.vaadin.domain.Currency;
import com.vaadin.domain.Wallet;
import com.vaadin.domain.WalletItem;
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

@Route(value = "walletItemForm", layout = MainLayout.class)
public class WalletItemForm extends FormLayout {

    ComboBox<Currency> currency = new ComboBox<>("Currency");
    NumberField quantity = new NumberField("Quantity");
    ComboBox<Wallet> wallet = new ComboBox<>("Wallet owner's name");

    Button save = new Button("Create");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<WalletItem> binder = new BeanValidationBinder<>(WalletItem.class);
    private WalletItem walletItem;

    public WalletItemForm(List<Wallet> walletsList, List<Currency> currenciesList) {
        addClassName("wallet-item-form");
        binder.bindInstanceFields(this);
        currency.setItems(currenciesList);
        currency.setItemLabelGenerator(Currency::name);
        wallet.setItems(walletsList);
        wallet.setItemLabelGenerator(Wallet::getName);
        quantity.setValue(1d);
        quantity.setHasControls(true);
        quantity.setMin(0);
        quantity.setMax(1000);
        quantity.setStep(0.1);

        add(
                currency,
                quantity,
                wallet,
                createButtonsLayout()
        );
    }

    public void setWalletItem(WalletItem walletItem) {
        this.walletItem = walletItem;
        binder.readBean(walletItem);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, walletItem)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(walletItem);
            fireEvent(new SaveEvent(this, walletItem));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class WalletItemFormEvent extends ComponentEvent<WalletItemForm> {
        private WalletItem walletItem;

        protected WalletItemFormEvent(WalletItemForm source, WalletItem walletItem) {
            super(source, false);
            this.walletItem = walletItem;
        }

        public WalletItem getWalletItem() {
            return walletItem;
        }
    }

    public static class SaveEvent extends WalletItemFormEvent {
        SaveEvent(WalletItemForm source, WalletItem walletItem) {
            super(source, walletItem);
        }
    }

    public static class DeleteEvent extends WalletItemFormEvent {
        DeleteEvent(WalletItemForm source, WalletItem walletItem) {
            super(source, walletItem);
        }

    }

    public static class CloseEvent extends WalletItemFormEvent {
        CloseEvent(WalletItemForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
