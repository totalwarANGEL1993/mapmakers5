package twa.siedelwood.s5.mapmaker.view.swing.component.panel.quest;

import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestBehavior;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationBehaviorModel;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationParamaterModel;
import twa.siedelwood.s5.mapmaker.service.config.BehaviorPrototypeService;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.BasicConfirmDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.ListSelectValueDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.input.InputComponent;
import twa.siedelwood.s5.mapmaker.view.swing.component.input.InputComponentFactory;
import twa.siedelwood.s5.mapmaker.view.swing.component.input.InputComponentFactoryImpl;
import twa.siedelwood.s5.mapmaker.view.swing.component.input.InputComponentMessageField;

import javax.swing.*;
import java.util.Vector;

/**
 *
 */
public class QuestDefaultBehaviorPropertiesDialog extends ListSelectValueDialog {
    protected ConfigurationBehaviorModel behaviorModel;
    protected QuestBehavior originalBehavior;
    protected Vector<InputComponent> components;
    protected InputComponentFactory inputComponentFactory;

    public QuestDefaultBehaviorPropertiesDialog(QuestBehavior behavior) {
        super(null);
        BehaviorPrototypeService behaviorService = ApplicationController.getInstance().getBehaviorPrototypeService();
        ConfigurationBehaviorModel behaviorModel = behaviorService.getBehaviorPrototype(behavior.getName());
        this.title = behavior.getName();
        this.behaviorModel = behaviorModel;
        this.originalBehavior = behavior;
        this.components = new Vector<>();
        this.inputComponentFactory = new InputComponentFactoryImpl();
    }

    @Override
    public void initDialog() {
        width = 400;
        height = 100 + (40 * behaviorModel.getParameters().size());

        for (ConfigurationParamaterModel p : behaviorModel.getParameters()) {
            if (p.getType().equals("MessageInput")) {
                height = height +20;
            }
        }

        dialog = new BasicConfirmDialog(this.frame, this.title) {
            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onConfirm() {
                QuestDefaultBehaviorPropertiesDialog.this.onConfirm();
            }
        };
        setSize(width, height);
        dialog.initDialog();


        int h = 40;
        int index = 0;
        for (ConfigurationParamaterModel paramaterModel : behaviorModel.getParameters()) {
            InputComponent component;
            if (!paramaterModel.getType().contains("Name")) {
                int defaultHeight = h;
                if (paramaterModel.getType().equals("MessageInput")) {
                    defaultHeight = defaultHeight + 20;
                }
                component = inputComponentFactory.getFromParameterData(width - 10, defaultHeight, paramaterModel);
                components.add(component);
            }
            else {
                Vector<String> names;
                switch (paramaterModel.getType()) {
                    case "BriefingName":
                        names = ApplicationController.getInstance().getCurrentProject().getBriefingCollection().toNamesVector();
                        component = inputComponentFactory.getFromParameterData(width - 10, h, paramaterModel, names);
                        components.add(component);
                        break;
                    case "QuestName":
                        names = ApplicationController.getInstance().getCurrentProject().getQuestCollection().toNamesVector();
                        component = inputComponentFactory.getFromParameterData(width - 10, h, paramaterModel, names);
                        components.add(component);
                        break;
                    case "ChoiceName":
                        names = ApplicationController.getInstance().getCurrentProject().getBriefingCollection().getAllChoicePageNames();
                        component = inputComponentFactory.getFromParameterData(width - 10, h, paramaterModel, names);
                        components.add(component);
                        break;
                    case "ValueName":
                        names = ApplicationController.getInstance().getCurrentProject().getQuestCollection().getAllCustomVariableNames();
                        component = inputComponentFactory.getFromParameterData(width - 10, h, paramaterModel, names);
                        components.add(component);
                        break;
                    case "ArmyName":
                        names = ApplicationController.getInstance().getCurrentProject().getQuestCollection().getAllArmyNames();
                        component = inputComponentFactory.getFromParameterData(width - 10, h, paramaterModel, names);
                        components.add(component);
                        break;
                    // ScriptName
                    default:
                        names = ApplicationController.getInstance().getSelectableValueService().getScriptNames();
                        component = inputComponentFactory.getFromParameterData(width - 10, h, paramaterModel, names);
                        components.add(component);
                }
            }
            component.setValue(originalBehavior.getParameter(index+1));
            index++;
        }

        int y = 10;
        for (InputComponent c : components) {
            JPanel panel = c.getPane();
            panel.setLocation(5, y);
            dialog.add(panel);
            y = y + h;
            if (c instanceof InputComponentMessageField) {
                y = y + 20;
            }
        }
    }

    public Vector<InputComponent> getComponents() {
        return components;
    }

    @Override
    public void onConfirm() {
        dialog.foreAccepted();
        dialog.dispose();
    }
}
