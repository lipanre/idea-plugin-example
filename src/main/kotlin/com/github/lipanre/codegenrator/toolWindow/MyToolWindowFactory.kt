package com.github.lipanre.codegenrator.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.lipanre.codegenrator.MyBundle
import com.github.lipanre.codegenrator.services.MyProjectService
import com.intellij.openapi.observable.properties.ObservableMutableProperty
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.text
import javax.swing.JButton


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

        private val service = toolWindow.project.service<MyProjectService>()

        private val dataModal = DataModal(MyBundle.message("randomLabel", "?"))

        private lateinit var panel: DialogPanel

        private val observer = object : ObservableMutableProperty<String> {


            override fun set(value: String) {
                dataModal.text = value
            }

            override fun get(): String {
                return dataModal.text
            }

        }

//        private val textField: JBTextField = JBTextField()

//        fun getContent() = JBPanel<JBPanel<*>>().apply {
//            val label = JBLabel(MyBundle.message("randomLabel", "?"))
//
//            add(label)
//            add(JButton(MyBundle.message("shuffle")).apply {
//                addActionListener {
//                    label.text = MyBundle.message("randomLabel", service.getRandomNumber())
//                }
//            })
//        }

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            panel = panel {
                row {
                    val textField = textField().bindText(observer)
                    button(MyBundle.message("shuffle")) {
                        thisLogger().warn("before data Modal text is: ${dataModal.text}")
                        observer.set(MyBundle.message("randomLabel", service.getRandomNumber()))
                        thisLogger().warn("after data Modal text is: ${dataModal.text}")
//                        textField.text(dataModal.text)
                        panel.apply()
                    }
                }
            }
            add(panel)
        }


        data class DataModal(var text: String)
    }
}
