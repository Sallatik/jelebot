# Jelebot - Telegram bot annotations for java

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
		<artifactId>jelebot</artifactId>
		<version>1.3.3-SNAPSHOT</version>
	</dependency>
</dependencies>
```

### Gradle

Add the following to your `build.gradle` file:

```groovy
allprojects {
    repositories {
        ...
        maven { proxyURL 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.Sallatik:jelebot:1.3.3-SNAPSHOT'
}
```

### Building from source

To build this library from source, you will need Git and Maven installed in your system.

1. Clone this repo: `git clone https://github.com/Sallatik/jelebot.git`
2. Change directory to the project root: `cd jelebot`
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

### Listeners

Use annotations in package `sallat.jelebot.annotation.listeners` to declare your methods as listeners.
```java
@MessageListener
void onMessage(Message message) {
    // handle incoming message
}

@InlineQueryListener
void onInlineQuery(InlineQuery callbackQuery) {
    // handle inline query
}
```
You can declare multiple listeners of the same type.  
Every annotation has it's own requirements for the method signature. See [javadoc](#generating-javadoc).
#### Filters
`MessageListener` annotation has `filter` element that allows to filter messages by their origin, content or other properties.  
See [javadoc](#generating-javadoc) for complete syntax reference.
```java
@MessageListener(filter = "private & photo & caption")
void onPrivatePhotoWithCaption(Message message) {
    // this method will only be called
    // for photos with caption sent via private messages
}
```
#### Return value
If your methods declares `BaseRequest` or it's subclass as a return type, the returned request will be executed right after the listener returns.
```java
@CallbackQueryListener
AnswerCallbackQuery onCallbackQuery(CallbackQuery query) {
    // this request will be executed
    return new AnswerCallbackQuery(query.id())
        .text("You pressed a button");
}
```
However, if you want to handle telegram's response to your request, you should execute it manually with `TelegramBot` instance.
### Injection
Jelebot will inject a `TelegramBot` instance, 
used to execute requests into the fields or setter methods annotated with `@InjectTelegramBot`
```java
@InjectTelegramBot
private TelegramBot bot;

@MessageListener(filter = "private & text")
void echo(Message messsage) {
    SendMessage request = new SendMessage(message.chat().id(), message.text());
    SendResponse response = bot.execute(request);
    // handle the response
}
```
### Jelebot
Use `Jelebot` class to start your bot.
1. Create an instance:
```java
Jelebot jelebot = Jelebot.create("bot-token");
```
2. Instantinate your annotated classes and register them.
```java
jelebot.register(new MyBotModule())
    // ...
    .register(new AnotherBotModule());
```
3. Specify the update source.
```java
jelebot.setUpdateSource(new LongPollingUpdateSource());
```
4. Start the bot.
```java
jelebot.start();
```
5. Stop the bot before exiting the application.
```java
jelebot.stop();
```
or
```java
Runtime.addShutdownHook(new Thread(() -> jelebot.stop()));
```
## Example
Here is a little bot that can rate your cat pics.
```java
class RateMyCatBot {
    
    @MessageListener("private & photo & /rate")
    SendMessage rateCat(Message message) {
        return SendMessage(message.chat().id(), "Your cat is awesome! 10/10");
    }
    
    public static void main(String [] args) {
        Jelebot.create("bot-token")
            .register(new RateMyCatBot())
            .setUpdateSource(new LongPollingUpdateSource())
            .start();
    }
}
```
## Contribution

You are welcome to contribute by reviewing my code and reporting the bugs.

