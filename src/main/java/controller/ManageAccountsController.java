package controller;

import model.validation.Notification;
import service.Service;
import service.account.AccountService;
import service.log.LogService;
import view.ManageAccountsView;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ManageAccountsController extends Controller {

    private static final String MANAGE_ACCOUNTS_VIEW = "manageAccountsView";
    private static final String ACCOUNT_SERVICE = "accountService";
    private static final String LOG_SERVICE = "logService";

    private ManageAccountsView manageAccountsView;
    private AccountService accountService;
    private LogService logService;

    public ManageAccountsController(Map<String, Controller> controllerMap, Map<String, View> viewMap, Map<String, Service> serviceMap) {
        super(controllerMap, viewMap, serviceMap);

        try{
            manageAccountsView = (ManageAccountsView) viewMap.get(MANAGE_ACCOUNTS_VIEW);
            accountService = (AccountService) serviceMap.get(ACCOUNT_SERVICE);
            logService = (LogService) serviceMap.get(LOG_SERVICE);
        } catch (Exception e){
            e.printStackTrace();
        }

        manageAccountsView.addViewAllActionListener(new ViewAllActionListener());
        manageAccountsView.addNewActionListener(new NewActionListener());
        manageAccountsView.addUpdateActionListener(new UpdateActionListener());
        manageAccountsView.addDeleteActionListener(new DeleteActionListener());
    }

    @Override
    public void display() {
        manageAccountsView.setVisible(true);
    }

    private class ViewAllActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = accountService.getAccountData();
            manageAccountsView.sendText(text);
            logService.saveLog("View accounts");
        }
    }

    private class NewActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int amount = Integer.parseInt(manageAccountsView.getAAmount());
                long clientId = Long.parseLong(manageAccountsView.getClientId());
                boolean isSaving = manageAccountsView.isSaving();
                double interest = isSaving?Double.parseDouble(manageAccountsView.getInterest()):0;

                Notification<Boolean> registerNotification = accountService.insertAccount(amount,clientId,isSaving,interest);
                if (registerNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), registerNotification.getFormattedErrors());
                } else {
                    if (!registerNotification.getResult()) {
                        JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), "Could not add account");
                    } else {
                        JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), "Account added!");
                        logService.saveLog("Added new account");
                    }
                }
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), "Invalid numbers entered!");
            }
        }
    }

    private class UpdateActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                long id = Long.parseLong(manageAccountsView.getAId());
                int amount = Integer.parseInt(manageAccountsView.getAAmount());
                long clientId = Long.parseLong(manageAccountsView.getClientId());
                boolean isSaving = manageAccountsView.isSaving();
                double interest = isSaving?Double.parseDouble(manageAccountsView.getInterest()):0;

                Notification<Boolean> registerNotification = accountService.updateAccount(id,amount,clientId,isSaving,interest);
                if (registerNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), registerNotification.getFormattedErrors());
                } else {
                    if (!registerNotification.getResult()) {
                        JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), "Could not update account");
                    } else {
                        JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), "Account updated!");
                        logService.saveLog("Updated account");
                    }
                }
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), "Invalid numbers entered!");
            }
        }
    }

    private class DeleteActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                long id = Long.parseLong(manageAccountsView.getAId());

                Notification<Boolean> registerNotification = accountService.delete(id);
                if (registerNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), registerNotification.getFormattedErrors());
                } else {
                    if (!registerNotification.getResult()) {
                        JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), "Could not delete account");
                    } else {
                        JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), "Account deleted!");
                        logService.saveLog("Deleted account");
                    }
                }
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(manageAccountsView.getContentPane(), "Invalid numbers entered!");
            }
        }
    }
}
