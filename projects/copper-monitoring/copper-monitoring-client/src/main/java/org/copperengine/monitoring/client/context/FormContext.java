/*
 * Copyright 2002-2014 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.copperengine.monitoring.client.context;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.copperengine.monitoring.client.adapter.GuiCopperDataProvider;
import org.copperengine.monitoring.client.context.FormBuilder.EngineFormBuilder;
import org.copperengine.monitoring.client.form.BorderPaneShowFormStrategie;
import org.copperengine.monitoring.client.form.EmptyShowFormStrategie;
import org.copperengine.monitoring.client.form.Form;
import org.copperengine.monitoring.client.form.FormCreator;
import org.copperengine.monitoring.client.form.FxmlForm;
import org.copperengine.monitoring.client.form.ShowFormStrategy;
import org.copperengine.monitoring.client.form.TabPaneShowFormStrategie;
import org.copperengine.monitoring.client.form.dialog.InputDialogCreator;
import org.copperengine.monitoring.client.form.filter.EmptyFilterModel;
import org.copperengine.monitoring.client.form.filter.FilterAbleForm;
import org.copperengine.monitoring.client.form.filter.FilterController;
import org.copperengine.monitoring.client.form.filter.FilterResultController;
import org.copperengine.monitoring.client.form.filter.GenericFilterController;
import org.copperengine.monitoring.client.form.filter.enginefilter.EngineFilterAbleForm;
import org.copperengine.monitoring.client.form.filter.enginefilter.EnginePoolFilterModel;
import org.copperengine.monitoring.client.form.filter.enginefilter.GenericEngineFilterController;
import org.copperengine.monitoring.client.form.issuereporting.IssueReporter;
import org.copperengine.monitoring.client.ui.adaptermonitoring.fiter.AdapterMonitoringFilterController;
import org.copperengine.monitoring.client.ui.adaptermonitoring.fiter.AdapterMonitoringFilterModel;
import org.copperengine.monitoring.client.ui.adaptermonitoring.result.AdapterMonitoringResultController;
import org.copperengine.monitoring.client.ui.adaptermonitoring.result.AdapterMonitoringResultModel;
import org.copperengine.monitoring.client.ui.audittrail.filter.AuditTrailFilterController;
import org.copperengine.monitoring.client.ui.audittrail.filter.AuditTrailFilterModel;
import org.copperengine.monitoring.client.ui.audittrail.result.AuditTrailResultController;
import org.copperengine.monitoring.client.ui.audittrail.result.AuditTrailResultModel;
import org.copperengine.monitoring.client.ui.custommeasurepoint.filter.CustomMeasurePointFilterController;
import org.copperengine.monitoring.client.ui.custommeasurepoint.filter.CustomMeasurePointFilterModel;
import org.copperengine.monitoring.client.ui.custommeasurepoint.result.CustomMeasurePointResultController;
import org.copperengine.monitoring.client.ui.custommeasurepoint.result.CustomMeasurePointResultModel;
import org.copperengine.monitoring.client.ui.dashboard.result.DashboardDependencyFactory;
import org.copperengine.monitoring.client.ui.dashboard.result.DashboardResultController;
import org.copperengine.monitoring.client.ui.dashboard.result.DashboardResultModel;
import org.copperengine.monitoring.client.ui.dashboard.result.engine.ProcessingEngineController;
import org.copperengine.monitoring.client.ui.dashboard.result.pool.ProccessorPoolController;
import org.copperengine.monitoring.client.ui.dashboard.result.provider.ProviderController;
import org.copperengine.monitoring.client.ui.databasemonitor.result.DatabaseMonitorResultController;
import org.copperengine.monitoring.client.ui.load.filter.EngineLoadFilterController;
import org.copperengine.monitoring.client.ui.load.filter.EngineLoadFilterModel;
import org.copperengine.monitoring.client.ui.load.result.EngineLoadResultController;
import org.copperengine.monitoring.client.ui.logs.filter.LogsFilterController;
import org.copperengine.monitoring.client.ui.logs.filter.LogsFilterModel;
import org.copperengine.monitoring.client.ui.logs.result.LogsResultController;
import org.copperengine.monitoring.client.ui.logs.result.LogsResultModel;
import org.copperengine.monitoring.client.ui.manage.HotfixController;
import org.copperengine.monitoring.client.ui.manage.HotfixModel;
import org.copperengine.monitoring.client.ui.measurepoint.result.MeasurePointResultController;
import org.copperengine.monitoring.client.ui.message.filter.MessageFilterController;
import org.copperengine.monitoring.client.ui.message.filter.MessageFilterModel;
import org.copperengine.monitoring.client.ui.message.result.MessageResultController;
import org.copperengine.monitoring.client.ui.message.result.MessageResultModel;
import org.copperengine.monitoring.client.ui.provider.filter.ProviderFilterController;
import org.copperengine.monitoring.client.ui.provider.filter.ProviderFilterModel;
import org.copperengine.monitoring.client.ui.provider.result.ProviderResultController;
import org.copperengine.monitoring.client.ui.provider.result.ProviderResultModel;
import org.copperengine.monitoring.client.ui.repository.filter.WorkflowRepositoryFilterController;
import org.copperengine.monitoring.client.ui.repository.filter.WorkflowRepositoryFilterModel;
import org.copperengine.monitoring.client.ui.repository.result.WorkflowRepositoryDependencyFactory;
import org.copperengine.monitoring.client.ui.repository.result.WorkflowRepositoryResultController;
import org.copperengine.monitoring.client.ui.settings.SettingsController;
import org.copperengine.monitoring.client.ui.settings.SettingsModel;
import org.copperengine.monitoring.client.ui.sql.filter.SqlFilterController;
import org.copperengine.monitoring.client.ui.sql.filter.SqlFilterModel;
import org.copperengine.monitoring.client.ui.sql.result.SqlResultController;
import org.copperengine.monitoring.client.ui.sql.result.SqlResultModel;
import org.copperengine.monitoring.client.ui.systemresource.filter.ResourceFilterController;
import org.copperengine.monitoring.client.ui.systemresource.filter.ResourceFilterModel;
import org.copperengine.monitoring.client.ui.systemresource.result.RessourceResultController;
import org.copperengine.monitoring.client.ui.workflowclasssesctree.WorkflowClassesTreeController;
import org.copperengine.monitoring.client.ui.workflowclasssesctree.WorkflowClassesTreeController.DisplayWorkflowClassesModel;
import org.copperengine.monitoring.client.ui.workflowclasssesctree.WorkflowClassesTreeForm;
import org.copperengine.monitoring.client.ui.workflowinstance.filter.WorkflowInstanceFilterController;
import org.copperengine.monitoring.client.ui.workflowinstance.filter.WorkflowInstanceFilterModel;
import org.copperengine.monitoring.client.ui.workflowinstance.result.WorkflowInstanceDependencyFactory;
import org.copperengine.monitoring.client.ui.workflowinstance.result.WorkflowInstanceResultController;
import org.copperengine.monitoring.client.ui.workflowinstance.result.WorkflowInstanceResultModel;
import org.copperengine.monitoring.client.ui.workflowsummary.filter.WorkflowSummaryFilterController;
import org.copperengine.monitoring.client.ui.workflowsummary.filter.WorkflowSummaryFilterModel;
import org.copperengine.monitoring.client.ui.workflowsummary.result.WorkflowSummaryDependencyFactory;
import org.copperengine.monitoring.client.ui.workflowsummary.result.WorkflowSummaryResultController;
import org.copperengine.monitoring.client.ui.workflowsummary.result.WorkflowSummaryResultModel;
import org.copperengine.monitoring.client.ui.worklowinstancedetail.filter.WorkflowInstanceDetailFilterController;
import org.copperengine.monitoring.client.ui.worklowinstancedetail.filter.WorkflowInstanceDetailFilterModel;
import org.copperengine.monitoring.client.ui.worklowinstancedetail.result.WorkflowInstanceDetailResultController;
import org.copperengine.monitoring.client.ui.worklowinstancedetail.result.WorkflowInstanceDetailResultModel;
import org.copperengine.monitoring.client.util.CodeMirrorFormatter;
import org.copperengine.monitoring.client.util.MessageKey;
import org.copperengine.monitoring.client.util.MessageProvider;
import org.copperengine.monitoring.client.util.WorkflowVersion;
import org.copperengine.monitoring.core.model.CopperInterfaceSettings;
import org.copperengine.monitoring.core.model.MeasurePointData;
import org.copperengine.monitoring.core.model.MonitoringDataProviderInfo;
import org.copperengine.monitoring.core.model.ProcessingEngineInfo;
import org.copperengine.monitoring.core.model.ProcessorPoolInfo;
import org.copperengine.monitoring.core.model.SystemResourcesInfo;
import org.copperengine.monitoring.core.model.WorkflowStateSummary;

public class FormContext implements DashboardDependencyFactory, WorkflowInstanceDependencyFactory, WorkflowRepositoryDependencyFactory,
        WorkflowSummaryDependencyFactory {
    protected final TabPane mainTabPane;
    protected final BorderPane mainPane;
    protected final FormCreator formGroup;
    protected final MessageProvider messageProvider;
    protected final GuiCopperDataProvider guiCopperDataProvider;
    protected final SettingsModel settingsModelSingleton;
    protected final CodeMirrorFormatter codeMirrorFormatterSingelton = new CodeMirrorFormatter();
    protected final IssueReporter issueReporter;

    private FxmlForm<SettingsController> settingsForSingleton;
    private FxmlForm<HotfixController> hotfixFormSingleton;
    private FilterAbleForm<EmptyFilterModel, DashboardResultModel> dasboardFormSingleton;
    private final InputDialogCreator inputDialogCreator;

    public FormContext(BorderPane mainPane, GuiCopperDataProvider guiCopperDataProvider, MessageProvider messageProvider, SettingsModel settingsModelSingleton, IssueReporter issueReporter, InputDialogCreator inputDialogCreator) {
        this.mainTabPane = new TabPane();
        this.messageProvider = messageProvider;
        this.guiCopperDataProvider = guiCopperDataProvider;
        this.mainPane = mainPane;
        this.settingsModelSingleton = settingsModelSingleton;
        this.issueReporter = issueReporter;
        this.inputDialogCreator = inputDialogCreator;

        CopperInterfaceSettings copperInterfaceSettings = guiCopperDataProvider.getInterfaceSettings();

        ArrayList <FormCreator> mainCreators = new ArrayList<FormCreator>();
        mainCreators.add(new FormCreator(messageProvider.getText(MessageKey.dashboard_title)) {
            @Override
            public Form<?> createForm() {
                return createDashboardForm();
            }
        });

        mainCreators.add(new FormCreator(messageProvider.getText(MessageKey.workflowGroup_title), createWorkflowGroup()));

        mainCreators.add(new FormCreator(messageProvider.getText(MessageKey.adapterMonitoring_title)) {
            @Override
            public Form<?> createForm() {
                return createAdapterMonitoringForm();
            }
        });

        mainCreators.add(new FormCreator(messageProvider.getText(MessageKey.workflowRepository_title)) {
            @Override
            public Form<?> createForm() {
                return createWorkflowRepositoryForm();
            }
        });
        mainCreators.add(new FormCreator(messageProvider.getText(MessageKey.message_title)) {
            @Override
            public Form<?> createForm() {
                return createMessageForm();
            }
        });
        mainCreators.add(new FormCreator(messageProvider.getText(MessageKey.logsGroup_title), createLogGroup()));

        mainCreators.add(new FormCreator(messageProvider.getText(MessageKey.loadGroup_title), createLoadGroup(copperInterfaceSettings)));

        FormCreator sqlformcreator = new FormCreator(messageProvider.getText(MessageKey.sql_title)) {
            @Override
            public Form<?> createForm() {
                return createSqlForm();
            }
        };
        if (!copperInterfaceSettings.isCanExecuteSql()) {
            sqlformcreator.setEnabled(false);
            sqlformcreator.setTooltip(new Tooltip("disabled in copper"));
        }
        mainCreators.add(sqlformcreator);

        mainCreators.add(new FormCreator(messageProvider.getText(MessageKey.hotfix_title)) {
            @Override
            public Form<?> createForm() {
                return createHotfixForm();
            }
        });
        mainCreators.add(new FormCreator(messageProvider.getText(MessageKey.settings_title)) {
            @Override
            public Form<?> createForm() {
                return createSettingsForm();
            }
        });
        formGroup = new FormCreator("", mainCreators);
    }

    public ArrayList<FormCreator> createLogGroup() {
        ArrayList<FormCreator> loggroup = new ArrayList<FormCreator>();
        loggroup.add(new FormCreator(messageProvider.getText(MessageKey.audittrail_title)) {
            @Override
            public Form<?> createForm() {
                return createAudittrailForm();
            }
        });
        loggroup.add(new FormCreator(messageProvider.getText(MessageKey.logs_title)) {
            @Override
            public Form<?> createForm() {
                return createLogsForm();
            }
        });
        loggroup.add(new FormCreator(messageProvider.getText(MessageKey.provider_title)) {
            @Override
            public Form<?> createForm() {
                return createProviderForm();
            }
        });
        return loggroup;
    }

    public ArrayList<FormCreator> createWorkflowGroup() {
        ArrayList<FormCreator> workflowgroup = new ArrayList<FormCreator>();
        workflowgroup.add(new FormCreator(messageProvider.getText(MessageKey.workflowOverview_title)) {
            @Override
            public Form<?> createForm() {
                return createWorkflowOverviewForm();
            }
        });
        workflowgroup.add(new FormCreator(messageProvider.getText(MessageKey.workflowInstance_title)) {
            @Override
            public Form<?> createForm() {
                return createWorkflowInstanceListForm();
            }
        });
        return workflowgroup;
    }

    public ArrayList<FormCreator> createLoadGroup(CopperInterfaceSettings copperInterfaceSettings) {
        ArrayList<FormCreator> loadCreator = new ArrayList<FormCreator>();
        loadCreator.add(new FormCreator(messageProvider.getText(MessageKey.engineLoad_title)) {
            @Override
            public Form<?> createForm() {
                return createEngineLoadForm();
            }
        });
        loadCreator.add(new FormCreator(messageProvider.getText(MessageKey.resource_title)) {
            @Override
            public Form<?> createForm() {
                return createRessourceForm();
            }
        });
        final FormCreator measurePointCreator = new FormCreator(messageProvider.getText(MessageKey.measurePoint_title)) {
            @Override
            public Form<?> createForm() {
                return createMeasurePointForm();
            }
        };
        loadCreator.add(measurePointCreator);
        if (!copperInterfaceSettings.getSupportedFeatures().isSupportsLoggingStatisticCollector()) {
            measurePointCreator.setEnabled(false);
            measurePointCreator.setTooltip(new Tooltip("not available in copper"));
        }


        loadCreator.add(new FormCreator(messageProvider.getText(MessageKey.customMeasurePoint_title)) {
            @Override
            public Form<?> createForm() {
                return createCustomMeasurePointForm();
            }
        });
        loadCreator.add(new FormCreator(messageProvider.getText(MessageKey.databaseMonitoring_title)) {
            @Override
            public Form<?> createForm() {
                return createDatabaseMonitoringForm();
            }
        });
        return loadCreator;
    }

    public void setupGUIStructure() {
        mainPane.setCenter(mainTabPane);
        // mainPane.setTop(createToolbar());

        mainPane.setTop(createMenueBar());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new FormCreator(messageProvider.getText(MessageKey.dashboard_title)) {
                    @Override
                    public Form<?> createForm() {
                        return createDashboardForm();
                    }
                }.show();
            }
        });
    }

    public MenuBar createMenueBar() {
        final MenuBar menuBar = new MenuBar();
        formGroup.createMenuBar(menuBar);
        return menuBar;
    }

    public ToolBar createToolbar() {
        ToolBar toolBar = new ToolBar();
        Region spacer = new Region();
        spacer.getStyleClass().setAll("spacer");

        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER);
        HBox.setHgrow(buttonBar, Priority.ALWAYS);
        buttonBar.getStyleClass().setAll("segmented-button-bar");

        List<Node> buttons = formGroup.createButtonList();
        buttons.get(0).getStyleClass().addAll("first");
        buttons.get(buttons.size() - 1).getStyleClass().addAll("last", "capsule");

        buttonBar.getChildren().addAll(buttons);
        toolBar.getItems().addAll(/* spacer, */buttonBar);
        toolBar.setCache(true);
        return toolBar;
    }

    public WorkflowClassesTreeForm createWorkflowClassesTreeForm(WorkflowSummaryFilterController filterController) {
        TreeView<DisplayWorkflowClassesModel> workflowView = new TreeView<DisplayWorkflowClassesModel>();
        WorkflowClassesTreeController workflowClassesTreeController = createWorkflowClassesTreeController(workflowView);
        return new WorkflowClassesTreeForm("", new EmptyShowFormStrategie(), workflowClassesTreeController,
                filterController, workflowView, guiCopperDataProvider);
    }

    @Override
    public WorkflowClassesTreeController createWorkflowClassesTreeController(TreeView<DisplayWorkflowClassesModel> workflowView) {
        return new WorkflowClassesTreeController(workflowView, issueReporter);
    }

    public FilterAbleForm<WorkflowSummaryFilterModel, WorkflowSummaryResultModel> createWorkflowOverviewForm(MenuItem... detailMenuItems) {
        return new EngineFormBuilder<WorkflowSummaryFilterModel, WorkflowSummaryResultModel, WorkflowSummaryFilterController, WorkflowSummaryResultController>(
                new WorkflowSummaryFilterController(this, getCachedAvailableEngines()),
                new WorkflowSummaryResultController(guiCopperDataProvider, this, detailMenuItems),
                this).build();
    }

    @Override
    public FilterAbleForm<WorkflowInstanceFilterModel, WorkflowInstanceResultModel> createWorkflowInstanceListForm() {
        final EngineFilterAbleForm<WorkflowInstanceFilterModel, WorkflowInstanceResultModel> form = new EngineFormBuilder<WorkflowInstanceFilterModel, WorkflowInstanceResultModel, WorkflowInstanceFilterController, WorkflowInstanceResultController>(
                new WorkflowInstanceFilterController(getCachedAvailableEngines()),
                new WorkflowInstanceResultController(guiCopperDataProvider, this, issueReporter),
                this
                ).build();
        form.setStaticTitle(messageProvider.getText(MessageKey.workflowOverview_title));
        return form;
    }

    public FilterAbleForm<MessageFilterModel, MessageResultModel> createMessageForm() {
        return new EngineFormBuilder<MessageFilterModel, MessageResultModel, MessageFilterController, MessageResultController>(
                new MessageFilterController(getCachedAvailableEngines()),
                new MessageResultController(guiCopperDataProvider),
                this).build();
    }

    public FilterAbleForm<WorkflowRepositoryFilterModel, WorkflowVersion> createWorkflowRepositoryForm() {
        return new EngineFormBuilder<WorkflowRepositoryFilterModel, WorkflowVersion, WorkflowRepositoryFilterController, WorkflowRepositoryResultController>(
                new WorkflowRepositoryFilterController(getCachedAvailableEngines()),
                new WorkflowRepositoryResultController(guiCopperDataProvider, this, codeMirrorFormatterSingelton),
                this).build();
    }

    @Override
    public FilterAbleForm<AuditTrailFilterModel, AuditTrailResultModel> createAudittrailForm() {
        return new FormBuilder<AuditTrailFilterModel, AuditTrailResultModel, AuditTrailFilterController, AuditTrailResultController>(
                new AuditTrailFilterController(),
                new AuditTrailResultController(guiCopperDataProvider, settingsModelSingleton, codeMirrorFormatterSingelton),
                this).build();
    }

    @Override
    public EngineFilterAbleForm<WorkflowInstanceDetailFilterModel, WorkflowInstanceDetailResultModel> createWorkflowInstanceDetailForm(String workflowInstanceId, ProcessingEngineInfo engineInfo) {
        FilterController<WorkflowInstanceDetailFilterModel> fCtrl = new WorkflowInstanceDetailFilterController(new WorkflowInstanceDetailFilterModel(workflowInstanceId, engineInfo), getCachedAvailableEngines());

        FxmlForm<FilterController<WorkflowInstanceDetailFilterModel>> filterForm = new FxmlForm<FilterController<WorkflowInstanceDetailFilterModel>>(fCtrl);

        FxmlForm<FilterResultController<WorkflowInstanceDetailFilterModel, WorkflowInstanceDetailResultModel>> resultForm = createWorkflowinstanceDetailResultForm(new EmptyShowFormStrategie());

        EngineFilterAbleForm<WorkflowInstanceDetailFilterModel, WorkflowInstanceDetailResultModel> filterAbleForm = new EngineFilterAbleForm<WorkflowInstanceDetailFilterModel, WorkflowInstanceDetailResultModel>(messageProvider,
                getDefaultShowFormStrategy(), filterForm, resultForm, issueReporter);
        filterAbleForm.displayedTitleProperty().bind(new SimpleStringProperty("Details Id:").concat(fCtrl.getFilter().workflowInstanceId));
        return filterAbleForm;
    }

    public FxmlForm<FilterResultController<WorkflowInstanceDetailFilterModel, WorkflowInstanceDetailResultModel>> createWorkflowinstanceDetailResultForm(ShowFormStrategy<?> showFormStrategy) {
        FilterResultController<WorkflowInstanceDetailFilterModel, WorkflowInstanceDetailResultModel> resCtrl = new WorkflowInstanceDetailResultController(guiCopperDataProvider, codeMirrorFormatterSingelton);
        return new FxmlForm<FilterResultController<WorkflowInstanceDetailFilterModel, WorkflowInstanceDetailResultModel>>("workflowInstanceDetail.title",
                        resCtrl, showFormStrategy);
    }

    @Override
    public FxmlForm<FilterResultController<WorkflowInstanceDetailFilterModel, WorkflowInstanceDetailResultModel>> createWorkflowinstanceDetailResultForm(BorderPane target) {
        return createWorkflowinstanceDetailResultForm(new BorderPaneShowFormStrategie(target));
    }

    public List<ProcessingEngineInfo> getCachedAvailableEngines() {
        if (engineList == null) {
            engineList = guiCopperDataProvider.getEngineList();
        }
        return engineList;
    }

    private FilterAbleForm<EngineLoadFilterModel, WorkflowStateSummary> engineLoadFormSingelton;

    public FilterAbleForm<EngineLoadFilterModel, WorkflowStateSummary> createEngineLoadForm() {
        FilterController<EngineLoadFilterModel> fCtrl = new EngineLoadFilterController(getCachedAvailableEngines());
        FxmlForm<FilterController<EngineLoadFilterModel>> filterForm = new FxmlForm<FilterController<EngineLoadFilterModel>>(fCtrl);

        FilterResultController<EngineLoadFilterModel, WorkflowStateSummary> resCtrl = new EngineLoadResultController(guiCopperDataProvider);
        FxmlForm<FilterResultController<EngineLoadFilterModel, WorkflowStateSummary>> resultForm =
                new FxmlForm<FilterResultController<EngineLoadFilterModel, WorkflowStateSummary>>(resCtrl);

        if (engineLoadFormSingelton == null) {
            engineLoadFormSingelton = new EngineFilterAbleForm<EngineLoadFilterModel, WorkflowStateSummary>(messageProvider,
                    getDefaultShowFormStrategy(), filterForm, resultForm, issueReporter);
        }
        return engineLoadFormSingelton;
    }

    public Form<SettingsController> createSettingsForm() {
        if (settingsForSingleton == null) {
            settingsForSingleton = new FxmlForm<SettingsController>("", new SettingsController(settingsModelSingleton), getDefaultShowFormStrategy());
        }
        return settingsForSingleton;
    }

    public Form<HotfixController> createHotfixForm() {
        if (hotfixFormSingleton == null) {
            hotfixFormSingleton = new FxmlForm<HotfixController>("", new HotfixController(new HotfixModel(), guiCopperDataProvider), getDefaultShowFormStrategy());
        }
        return hotfixFormSingleton;
    }

    public FilterAbleForm<SqlFilterModel, SqlResultModel> createSqlForm() {
        FilterAbleForm<SqlFilterModel, SqlResultModel> filterAbleForm = new FormBuilder<SqlFilterModel, SqlResultModel, SqlFilterController, SqlResultController>(
                new SqlFilterController(codeMirrorFormatterSingelton),
                new SqlResultController(guiCopperDataProvider),
                this
                ).build();
        return filterAbleForm;
    }

    FilterAbleForm<ResourceFilterModel, SystemResourcesInfo> ressourceFormSingelton = null;
    private List<ProcessingEngineInfo> engineList;

    public FilterAbleForm<ResourceFilterModel, SystemResourcesInfo> createRessourceForm() {
        if (ressourceFormSingelton == null) {
            ressourceFormSingelton = new FormBuilder<ResourceFilterModel, SystemResourcesInfo, ResourceFilterController, RessourceResultController>(
                    new ResourceFilterController(),
                    new RessourceResultController(guiCopperDataProvider),
                    this
                    ).build();
        }
        return ressourceFormSingelton;
    }

    public FilterAbleForm<EmptyFilterModel, DashboardResultModel> createDashboardForm() {
        if (dasboardFormSingleton == null) {
            dasboardFormSingleton = new FormBuilder<EmptyFilterModel, DashboardResultModel, GenericFilterController<EmptyFilterModel>, DashboardResultController>(
                    new GenericFilterController<EmptyFilterModel>(5000),
                    new DashboardResultController(guiCopperDataProvider, this),
                    this
                    ).build();
        }
        return dasboardFormSingleton;
    }

    public Form<ProccessorPoolController> createPoolForm(TabPane tabPane, ProcessingEngineInfo engine, ProcessorPoolInfo pool) {
        return new FxmlForm<ProccessorPoolController>(pool.getId(), new ProccessorPoolController(engine, pool, this, guiCopperDataProvider, inputDialogCreator), new TabPaneShowFormStrategie(tabPane, true));
    }

    @Override
    public Form<ProcessingEngineController> createEngineForm(TabPane tabPane, ProcessingEngineInfo engine, DashboardResultModel model) {
        return new FxmlForm<ProcessingEngineController>(engine.getId(), new ProcessingEngineController(engine, model, this, guiCopperDataProvider, inputDialogCreator), new TabPaneShowFormStrategie(tabPane));
    }

    public FilterAbleForm<EnginePoolFilterModel, MeasurePointData> createMeasurePointForm() {
        return new EngineFormBuilder<EnginePoolFilterModel, MeasurePointData, GenericEngineFilterController<EnginePoolFilterModel>, MeasurePointResultController>(
                new GenericEngineFilterController<EnginePoolFilterModel>(new EnginePoolFilterModel(), getCachedAvailableEngines()),
                new MeasurePointResultController(guiCopperDataProvider),
                this).build();
    }

    public FilterAbleForm<AdapterMonitoringFilterModel, AdapterMonitoringResultModel> createAdapterMonitoringForm() {
        return new FormBuilder<AdapterMonitoringFilterModel, AdapterMonitoringResultModel, AdapterMonitoringFilterController, AdapterMonitoringResultController>(
                new AdapterMonitoringFilterController(),
                new AdapterMonitoringResultController(guiCopperDataProvider),
                this).build();
    }

    protected ShowFormStrategy<?> getDefaultShowFormStrategy() {
        return new TabPaneShowFormStrategie(mainTabPane);
    }

    public FilterAbleForm<CustomMeasurePointFilterModel, CustomMeasurePointResultModel> createCustomMeasurePointForm() {
        return new FormBuilder<CustomMeasurePointFilterModel, CustomMeasurePointResultModel, CustomMeasurePointFilterController, CustomMeasurePointResultController>(
                new CustomMeasurePointFilterController(guiCopperDataProvider),
                new CustomMeasurePointResultController(guiCopperDataProvider),
                this).build();
    }

    public FilterAbleForm<LogsFilterModel, LogsResultModel> createLogsForm() {
        return new FormBuilder<LogsFilterModel, LogsResultModel, LogsFilterController, LogsResultController>(
                new LogsFilterController(),
                new LogsResultController(guiCopperDataProvider),
                this).build();
    }

    public FilterAbleForm<ProviderFilterModel, ProviderResultModel> createProviderForm() {
        return new FormBuilder<ProviderFilterModel, ProviderResultModel, ProviderFilterController, ProviderResultController>(
                new ProviderFilterController(),
                new ProviderResultController(guiCopperDataProvider),
                this).build();
    }

    public FilterAbleForm<EmptyFilterModel, String> createDatabaseMonitoringForm() {
        return new FormBuilder<EmptyFilterModel, String, GenericFilterController<EmptyFilterModel>, DatabaseMonitorResultController>(
                new GenericFilterController<EmptyFilterModel>(null),
                new DatabaseMonitorResultController(guiCopperDataProvider),
                this).build();
    }

    @Override
    public Form<ProviderController> createMonitoringDataProviderForm(MonitoringDataProviderInfo monitoringDataProviderInfo, BorderPane target) {
        return new FxmlForm<ProviderController>("", new ProviderController(monitoringDataProviderInfo, this, guiCopperDataProvider), new BorderPaneShowFormStrategie(target));
    }

}
