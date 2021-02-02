package com.vaadin.ui;

import com.vaadin.dto.WalletDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

@Route(value = "walletForm", layout = MainLayout.class)
public class WalletForm extends FormLayout {

    TextField name = new TextField("Name");

    Button save = new Button("Create");
    Button delete = new Button("Delete");
    Button edit = new Button("Edit");
    Button close = new Button("Cancel");

    Binder<WalletDto> binder = new BeanValidationBinder<>(WalletDto.class);
    private WalletDto walletDto;

    public WalletForm() {
        addClassName("wallet-form");
        binder.bindInstanceFields(this);

        add(
                name,
                createButtonsLayout()
        );
    }

    public void setWallet(WalletDto walletDto) {
        this.walletDto = walletDto;
        binder.readBean(walletDto);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, walletDto)));
        edit.addClickListener(click -> fireEvent(new EditEvent(this, walletDto)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, edit, delete, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(walletDto);
            fireEvent(new SaveEvent(this, walletDto));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class WalletFormEvent extends ComponentEvent<WalletForm> {
        private final WalletDto walletDto;

        protected WalletFormEvent(WalletForm source, WalletDto walletDto) {
            super(source, false);
            this.walletDto = walletDto;
        }

        public WalletDto getWalletDto() {
            return walletDto;
        }
    }

    public static class SaveEvent extends WalletFormEvent {
        SaveEvent(WalletForm source, WalletDto walletDto) {
            super(source, walletDto);
        }
    }

    public static class DeleteEvent extends WalletFormEvent {
        DeleteEvent(WalletForm source, WalletDto walletDto) {
            super(source, walletDto);
        }

    }

    public static class EditEvent extends WalletFormEvent {
        EditEvent(WalletForm source, WalletDto walletDto) {
            super(source, walletDto);
        }
    }

    public static class CloseEvent extends WalletFormEvent {
        CloseEvent(WalletForm source) {
            super(source, null);
        }
    }
}
