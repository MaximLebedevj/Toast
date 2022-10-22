package com.example.demo

import javafx.animation.FadeTransition
import javafx.animation.TranslateTransition
import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.media.AudioClip
import javafx.scene.media.Media
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration
import java.nio.file.Paths
import kotlin.system.exitProcess

//notifications

enum class ImageStyle {
    CIRCLE, RECTANGLE
}

enum class WindowPosition {
    UPPER_LEFT, UPPER_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
}

enum class OpenSound {
    MUSIC, KNOCK, DRUMS
}

enum class Animations {
    FADE, TRANSITION
}

class Config {
    var alpha = 0.9
    var openTime = 4900.0
    var imageType = ImageStyle.CIRCLE
    var title = "TITLE"
    var message = "MESSAGE"
    var appName = "APP NAME"
    var image =
        "https://creativereview.imgix.net/content/uploads/2018/10/13.jpg?auto=compress,format&q=60&w=1200&h=1217"
    var openSound = OpenSound.KNOCK
    var windowPosition = WindowPosition.UPPER_RIGHT
    var animationType = Animations.TRANSITION
    var addButtons = true
}

class Toast {
    private var config = Config()
    private var windows = Stage()
    private var root = BorderPane()
    private var box = HBox()
    private var primaryScreenBounds = Screen.getPrimary().visualBounds

    class Builder {
        private var config = Config()

        fun setTitle(str: String): Builder {
            config.title = str
            return this
        }

        fun setMessage(str: String): Builder {
            config.message = str;
            return this
        }

        fun setAppName(str: String): Builder {
            config.appName = str
            return this
        }

        fun build(): Toast {
            val toast = Toast()
            toast.config = config
            toast.build()
            return toast
        }
    }

    private fun build() {
        windows.initStyle(StageStyle.TRANSPARENT)
        windows.scene = Scene(root)
        windows.scene.fill = Color.TRANSPARENT
        windows.sizeToScene()

        root.style = "-fx-background-color: #000000; -fx-border-width: 3; -fx-border-color: blue;" +
                "-fx-padding: 10, 0, 0, 0 "
        root.setPrefSize(windows.scene.width, windows.scene.height)

        setImage()

        val vbox = VBox()

        val title = Label(config.title)
        title.style =
            ("-fx-font-family: fantasy; -fx-font-size: 18px; -fx-text-fill : red; -fx-label-padding: 0.9em 0 0 1.2em; -fx-underline: true;")
        title.setMinSize(125.0, 30.0)

        val message = Label(config.message)
        message.style =
            ("-fx-font-style: italic; -fx-font-size: 16px; -fx-text-fill : orange; -fx-label-padding: 0.05em 0 0 1.2em")

        val appName = Label(config.appName)
        appName.style =
            ("-fx-font-style: italic; -fx-font-size: 16px; -fx-text-fill : yellow; -fx-label-padding: 0.05em 0 0.5em 1.2em")

        vbox.children.addAll(title, message, appName)
        box.children.add(vbox)
        root.center = box

        if (config.addButtons) {
            val hbox = HBox()
            val button = Button("Ok")
            val button2 = Button("Close")
            button.style = (" -fx-background-color: #3c7fb1; -fx-padding: 3 30 3 30; -fx-text-fill: black;")
            button.setMinSize(100.0, 20.0)
            button2.style = (" -fx-background-color: orange; -fx-padding: 3 20 3 20; -fx-text-fill: black;")
            button2.setMinSize(100.0, 20.0)
            hbox.style = ("-fx-padding: 1 0 20 110")
            hbox.spacing = 10.0

            hbox.children.addAll(button, button2)
            box.children.add(hbox)
            root.bottom = hbox
        }

    }

    private fun setImage() {
        if (config.image.isEmpty()) {
            return
        }
        val iconBorder = if (config.imageType == ImageStyle.RECTANGLE) {
            Rectangle(100.0, 100.0)
        } else {
            Circle(50.0, 50.0, 50.0)
        }

        val image = Image(config.image)
        iconBorder.fill = ImagePattern(image)
        box.children.add(iconBorder)
    }

    private fun playSound() {

        val soundPath = if (config.openSound == OpenSound.MUSIC) {
            "1.mp3"
        } else {
            if (config.openSound == OpenSound.KNOCK) {
                "2.mp3"
            } else {
                "3.mp3"
            }
        }
        val media = Media(Paths.get(soundPath).toUri().toString())
        val mediaPlayer = AudioClip(media.source)
        mediaPlayer.play()
    }

    private fun openAnimation() {
        playSound()
        when (config.windowPosition) {
            WindowPosition.UPPER_LEFT -> {
                windows.x = 0.0
                windows.y = 0.0
            }

            WindowPosition.UPPER_RIGHT -> {
                windows.x = primaryScreenBounds.width - windows.scene.width
                windows.y = 0.0
            }

            WindowPosition.BOTTOM_LEFT -> {
                windows.x = 0.0
                windows.y = primaryScreenBounds.height - windows.scene.height
            }

            WindowPosition.BOTTOM_RIGHT -> {
                windows.x = primaryScreenBounds.width - windows.scene.width
                windows.y = primaryScreenBounds.height - windows.scene.height
            }
        }


        if (config.animationType == Animations.TRANSITION) {
            val anim = TranslateTransition(Duration.millis(1500.0), root)
            when (config.windowPosition) {
                WindowPosition.UPPER_LEFT -> {
                    anim.fromX = -windows.scene.width
                    anim.byX = windows.scene.width
                }

                WindowPosition.UPPER_RIGHT -> {
                    anim.fromX = windows.scene.width
                    anim.byX = -windows.scene.width
                }

                WindowPosition.BOTTOM_LEFT -> {
                    anim.fromX = -windows.scene.width
                    anim.byX = windows.scene.width
                }

                WindowPosition.BOTTOM_RIGHT -> {
                    anim.fromX = windows.scene.width
                    anim.byX = -windows.scene.width
                }
            }
            anim.play()
        } else {
            if (config.animationType == Animations.FADE) {
                val anim = FadeTransition(Duration.millis(1500.0), root)
                anim.fromValue = 0.0
                anim.toValue = config.alpha
                anim.cycleCount = 1
                anim.play()
            }
        }
    }

    private fun closeAnimation() {
        if (config.animationType == Animations.TRANSITION) {
            val anim = TranslateTransition(Duration.millis(1500.0), root)
            when (config.windowPosition) {
                WindowPosition.UPPER_LEFT -> {
                    anim.byX = -windows.scene.width
                }

                WindowPosition.UPPER_RIGHT -> {
                    anim.byX = windows.scene.width
                }

                WindowPosition.BOTTOM_LEFT -> {
                    anim.byX = -windows.scene.width
                }

                WindowPosition.BOTTOM_RIGHT -> {
                    anim.byX = windows.scene.width
                }
            }
            anim.onFinished = EventHandler {
                Platform.exit()
                exitProcess(0)
            }
            anim.play()
        } else {
            if (config.animationType == Animations.FADE) {
                val anim = FadeTransition(Duration.millis(1500.0), root)
                anim.fromValue = config.alpha
                anim.toValue = 0.0
                anim.cycleCount = 1
                anim.onFinished = EventHandler {
                    Platform.exit()
                    exitProcess(0)
                }
                anim.play()
            }
        }
    }

    fun start() {
        windows.show()
        openAnimation()
        val thread = Thread {
            try {
                Thread.sleep(config.openTime.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            closeAnimation()
        }
        Thread(thread).start()
    }

}

class SomeClass : Application() {
    override fun start(p0: Stage?) {
        val toast = Toast.Builder()
            .setTitle("Notification")
            .setMessage("Iron Man 2")
            .setAppName("AC/DC")
            .build()
        toast.start()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(SomeClass::class.java)

        }
    }
}
