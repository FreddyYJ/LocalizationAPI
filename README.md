# LocalizationAPI
LocalizationAPI is Minecraft Spigot plugin for player localization. It supports translation by using language files.

Current version is 0.1.0.

I need lots of test, feedbacks and reports. Please comment at plugin page, or Github issue.

## How to install
Installation of this plugin is same as other plugins. But, it needs default language file called 'en_us.json'.
1. install .jar file under plugins folder.
2. Run server.
3. Stop server, and ``` en_us.json ``` will be created in plugins/LocalizationAPI/lang.
4. Add your custom translations in ``` en_us.json ```.
5. Run server again, and enjoy!

For developers, visit wiki or [docs](https://freddyyj.github.io/LocalizationAPI/).

## Commands
```/lang```
* Show all commands that this player can use.

```/lang get [player]```
* Get player language code. Default is command sender.

```/lang set <language code> [player]```
* Set player language code. Default player is command sender.

```/lang test```
* Print text message with sender's language.

```/lang list```
* Show all language list.

```/lang available```
* Show all languages that can created.

```/lang create <language code>```
* Create new language file.

# Permissions
```localization.get```
* Permission to use ```/lang get```

```localization.get.other```
* Permission to use ```/lang get [player]```

```localization.set```
* Permission to use ```/lang set <language code>```

```localization.set.other```
* Permission to use ```/lang set <language code> [player]```

```localization.test```
* Permission to use ```/lang test```

```localization.list```
* Permission to use ```/lang list```

```localization.available```
* Permission to use ```/lang available```

```localization.create```
* Permission to use ```/lang create <language code>```

## Changelog
v0.2.0
* Add ```/lang available``` command to get all languages that can created.
* Add ```/lang create <langiage code>``` command to create new language file if it's included in ```/lang available```.
* Change package structure for improvement.
* All supported languages are added in JSON file in .jar.

v0.1.0
* Init plugin
