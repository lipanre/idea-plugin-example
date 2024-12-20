package com.github.lipanre.code.generator.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.lipanre.code.generator.MyBundle
import com.github.lipanre.code.generator.services.MyProjectService
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.text
import kotlinx.coroutines.flow.MutableStateFlow
import javax.swing.JButton
import javax.swing.JPanel


class MyToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

//        private val service = toolWindow.project.service<MyProjectService>()
//
//        private val dataModal = DataModal(MyBundle.message("randomLabel", "?"))
//
//        private lateinit var panel: DialogPanel


//        fun getContent() = JBPanel<JBPanel<*>>().apply {
//            panel = panel {
//                row {
//                    val textField = textField().bindText(dataModal::text)
//                    button(MyBundle.message("shuffle")) {
//                        textField.text(MyBundle.message("randomLabel", service.getRandomNumber()))
//                        panel.apply()
//                        thisLogger().warn("after apply data Modal text is: ${dataModal.text}")
//                    }
//                }
//            }
//            add(panel)
//        }
//
//        internal data class DataModal(var text: String)

        private val propertyGraph = PropertyGraph()
        private val dataModelTextProperty = propertyGraph.property("Initial Value")

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val panel = panel {
                row("Input:") {
                    textField()
                        .bindText(dataModelTextProperty)
                }

                row {
                    button("Update Text") {
                        dataModelTextProperty.set("Updated Value")
                    }
                }

                row("Current Value:") {
                    label("").bindText(dataModelTextProperty)
//                    dataModelTextProperty.afterChange { newValue ->
//                        label(newValue)
//                    }
                }
            }
            add(panel)
        }
    }
}