package com.maxim.toastwindow

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
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration
import java.awt.Dimension
import java.awt.Toolkit
import java.nio.file.Paths

//notifications

enum class ImageStyle {
    CIRCLE, RECTANGLE
}

enum class WindowPosition {
    UPPER_LEFT, UPPER_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
}

class Config {
    var alpha = 0.9
    var openTime = 4900.0
    var windowWidth = 300.0
    var windowHeight = 150.0
    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    var imageType = ImageStyle.CIRCLE
    var title = "TITLE"
    var message = "MESSAGE"
    var appName = "APP NAME"
    var image = "https://creativereview.imgix.net/content/uploads/2018/10/13.jpg?auto=compress,format&q=60&w=1200&h=1217"
    var media = "1.mp3"
    var windowPosition = WindowPosition.BOTTOM_RIGHT
}

class Toast {
    private var config = Config()
    private val windows = Stage()
    private var root = BorderPane()
    private var box = HBox()


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

        fun build(): Toast  {
            var toast = Toast()
            toast.config = config
            toast.build()

            return toast
        }
    }


    private fun build() {
        windows.initStyle(StageStyle.TRANSPARENT)

        val width = config.windowWidth

        val height = config.windowHeight

        val edgeRight = (config.screenSize.width - width)
        val edgeBottom = (config.screenSize.height - height)

        when(config.windowPosition) {
            WindowPosition.UPPER_LEFT ->  {
                windows.x = 0.0
                windows.y = 0.0
            }
            WindowPosition.UPPER_RIGHT -> {
                windows.x = edgeRight
                windows.y = 0.0
            }
            WindowPosition.BOTTOM_LEFT -> {
                windows.x = 0.0
                windows.y = edgeBottom
            }
            WindowPosition.BOTTOM_RIGHT -> {
                windows.x = edgeRight;
                windows.y = edgeBottom
            }
        }

        windows.scene = Scene(root, width, height)
        windows.scene.fill = Color.TRANSPARENT
        //windows.scene.stylesheets.add("assets/css/style.css")

        root.style = "-fx-background-color: #000000"
        root.setPrefSize(width, height)

        setImage()

        val vbox = VBox()

        val title = Label(config.title)
        title.style = ("-fx-font-family: fantasy; -fx-font-size: 18px; -fx-text-fill : red; -fx-label-padding: 0.9em 0 0 1.2em; -fx-underline: true;")

        val message = Label(config.message)
        message.style = ("-fx-font-style: italic; -fx-font-size: 16px; -fx-text-fill : orange; -fx-label-padding: 0.05em 0 0 1.2em")

        val appName = Label(config.appName)
        appName.style = ("-fx-font-style: italic; -fx-font-size: 16px; -fx-text-fill : yellow; -fx-label-padding: 0.05em 0 0.5em 1.2em")

        vbox.children.addAll(title, message, appName)
        box.children.add(vbox)
        root.center = box

        val button = Button("Ok")
        button.style = ("-fx-background-color: yellow")
        vbox.children.add(button)

        //val config = Config()
        //val media = Media(Paths.get(config.media).toUri().toString())
        //val mediaPlayer = MediaPlayer(media)
    }

    private fun setImage() {
        if (config.image.isEmpty()) {
            return
        }

        val iconBorder = if (config.imageType == ImageStyle.RECTANGLE) {
            Rectangle(100.0, 100.0)
        }
        else {
            Circle(50.0, 50.0, 50.0)
        }
        iconBorder.setFill(ImagePattern(Image(config.image)))
        box.children.add(iconBorder)
    }

    private fun playSound() {
        /*val media = Media(Paths.get(config.media).toUri().toString())
        val mediaPlayer = MediaPlayer(media)
        mediaPlayer.cycleCount = 1;
        mediaPlayer.volume = 2.0
        mediaPlayer.play()*/

        var bip = config.media
        val hit = Media(Paths.get(bip).toUri().toString())
        val mediaPlayer = AudioClip(hit.source)
        mediaPlayer.play();
    }

    private fun openTranslateTransition() {
        playSound()
        val anim = TranslateTransition()
        anim.duration = Duration.seconds(1.25)
        anim.node = root
        when (config.windowPosition)  {
            WindowPosition.UPPER_LEFT -> {
                anim.fromX = -config.windowWidth
                anim.toX = 0.0
            }
            WindowPosition.UPPER_RIGHT -> {
                anim.fromX = config.windowWidth
                anim.toX = 0.0
            }
            WindowPosition.BOTTOM_LEFT -> {
                anim.fromX = -config.windowWidth
                anim.toX = 0.0
            }
            WindowPosition.BOTTOM_RIGHT -> {
                anim.fromX = config.windowWidth
                anim.toX = 0.0
            }
        }
        anim.play()
    }

    private fun closeTranslateTransition() {
        val anim = TranslateTransition()
        anim.duration = Duration.seconds(1.25)
        anim.node = root
        when (config.windowPosition)  {
            WindowPosition.UPPER_LEFT -> {
                anim.fromX = -config.windowWidth
                anim.toX = 0.0
            }
            WindowPosition.UPPER_RIGHT -> {
                anim.fromX = config.windowWidth
                anim.toX = 0.0
            }
            WindowPosition.BOTTOM_LEFT -> {
                anim.fromX = -config.windowWidth
                anim.toX = 0.0
            }
            WindowPosition.BOTTOM_RIGHT -> {
                anim.fromX = 0.0
                anim.toX = config.windowWidth
            }
        }
        anim.onFinished = EventHandler {
            Platform.exit()
            System.exit(0)
        }
        anim.play()
    }

    private fun openAnimation() {
        playSound()
        val anim = FadeTransition(Duration.millis(1500.0), root)
        anim.fromValue = 0.0
        anim.toValue = config.alpha
        anim.cycleCount = 1
        anim.play()
    }
    private fun closeAnimation() {
        val anim = FadeTransition(Duration.millis(1500.0), root)
        anim.fromValue = config.alpha
        anim.toValue = 0.0
        anim.cycleCount = 1
        anim.onFinished = EventHandler {
            Platform.exit()
            System.exit(0)
        }
        anim.play()
    }

    fun start() {
        windows.show()
        openTranslateTransition()
        val thread = Thread {
            try {
                Thread.sleep(config.openTime.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            closeTranslateTransition()
        }
        Thread(thread).start()
    }

}


class SomeClass: Application() {
    override fun start(p0: Stage?) {
        var toast = Toast.Builder()
            .setTitle("New notification")
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
