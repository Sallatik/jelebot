# Telegram Bot Annotations for Java

This little annotation-based framework aims to simplify development of telegram bots, and make your code cleaner.
It is built on top of [Pengrad's library](https://github.com/pengrad/java-telegram-bot-api) for interactions with Telegram Bots API.

## How do I get it?

There are few ways to add this library to your project.

### Maven

To add this library to your maven build, you can use [Jitpack](https://jitpack.io) repository.
Add the following to your `pom.xml` file:

```xml
<repositories>
	<!-- other repositories... --> 
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependencies>
	<!-- other dependencies... -->
	<dependency>
		<groupId>com.github.Sallatik</groupId>
		<artifactId>tgbot-annotation</artifactId>
		<version>1.1-SNAPSHOT</version>
	</dependency>
</dependencies>
```

### Gradle

Add the following to your `build.gradle` file:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
	
dependencies {
    implementation 'com.github.Sallatik:tgbot-annotation:1.1-SNAPSHOT'
}
```

### Building from source

To build this library from source, you will need Git and Maven installed in your system.

1. Clone this repo: `git clone https://github.com/Sallatik/tgbot-annotation.git`
2. Change directory to the project root: `cd tgbot-annotation`
3. Build the project with Maven: `mvn package`
4. Find your jar in the target directory.

Note that to use this jar you will also have to downlod all dependencies and add them to your classpath.
I highly recommend to use gradle or maven for managing dependencies instead of messing with jars.

## Dependencies

- [Java Telegram Bot Api](https://github.com/pengrad/java-telegram-bot-api)
- [Predicate Parser](https://github.com/Sallatik/predicate-parser)

All the information about relevant versions and particular artifacts can be found in the `pom.xml` file.

## Documentation

- [Telegram Bot API](https://core.telegram.org/bots/api) - original documentation of the API
- [Java Telegram Bot API](https://github.com/pengrad/java-telegram-bot-api/blob/master/README.md) - documentation of the underlying wrapper library.
- [This file](#usage)
- Javadoc - can be generated from source.

### Generating javadoc 

1. Execute steps 1 and 2 from [Building from source](#building-from-source) instruction
2. Generate documentation using `mvn javadoc:javadoc`
3. Find your documentation in the target/site directory.
4. Open index.html file using your favorite browser.

## Usage

Creating telegram bots never was that simple. All you need to do:

- Create a class with annotated listener methods. Annotations in `sallat.tgbot.annotation` allow you to react to different kinds of events.
```java
import com.pengrad.telegrambot.model.Message;
import sallat.tgbot.annotation.*;

public class ConsoleLoggerBot {

	@MessageListener
	public void onMessage(Message message) {

		System.out.println(message.text());
	}
}
```

- Create a `Dispatcher` instance and register your listeners.
```java
Dispatcher dispatcher = Dispatcher.create();
dispatcher.register(new ConsoleLoggerBot());
```

- Create a `TelegramBot` instance and set the dispatcher as an updates listener.
```java
new TelegramBot("your-token-from-@BotFather")
	.setUpdatesListener(dispatcher);
```

- That's it!

### But I want to do %stuff-bots-ususally-do%, not just print messages to console!

No problem! The underlying Telegram Bot Api Java library allows you to do pretty much everything a bot can possibly do.
Check the official [documentation](https://github.com/pengrad/java-telegram-bot-api#usage).

I will just show one example on how to use it together with this framework:

Class with listeners: 
```java
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import sallat.tgbot.annotation.*;

public class HelloSayerBot {

	private TelegramBot bot;

	@MessageListener
	public void sayHello(Message message) {

		bot.execute(new SendMessage(message.chat().id(), "Hello!"));
	}

	public HelloSayerBot(TelegramBot bot) {

		this.bot = bot;
	}
}
```

The main method:
```java
public static void main(String [] args) {

	TelegramBot bot = new TelegramBot("your-token-from-@BotFather");
	HelloSayerBot listeners = new HelloSayerBot(bot);
	Dispatcher dispatcher = Dispatcher.create();
	dispatcher.register(listeners);
	bot.setUpdatesListener(dispatcher);
}
```

### Now, what if I only want to listen for private messages?

Just make use of **filters**. 
```java
@MessageListener(filter="private")
public void onPrivateMessage(Message message) {
    System.out.println("got a private message");
}
```

### Where do I find all available filters?

In the [javadoc](#generating-javadoc) for `MessageListener` annotation.

### Can I apply multiple filters?

You can combine as many filters as you want using boolean operators.
```java
@MessageListener(filter="private or channel")
public void onPrivateMessageOrChannelPost(Message message) {
    System.out.println("got private message");
    System.out.println("or a channel post, I'm not sure");
}

@MessageListener(filter="channel and photo")
public void onChannelPostPhoto(Message message) {
    System.out.println("got a photo from a channel");
}

@MessageListener(filter="(private and sticker) or ((group or supergroup) and text)")
public void crazyFilteredListener(Message message) {
    System.out.println("got a sticker in my dm");
    System.out.println("or a text message in some group or supergroup");
}

@MessageListener(filter="reply xor text")
public void confusedListener(Message message) {
    System.out.println("got a message that is a reply but not text");
    System.out.println("or a text message that is not a reply");
    System.out.println("but not both nor neither for sure");
}
```

You got the idea. Check the [javadoc](#generating-javadoc)!
## Contribution

You are welcome to contribute by reviewing my code and reporting the bugs.

