# Приложение для поиска GIF
### Описание
* Данное Java-приложение представляет собой простой инструмент для поиска изображений GIF с использованием API Giphy. Пользовательский интерфейс приложения позволяет легко выполнять поиск GIF-изображений, просматривать результаты и сохранять выбранные изображения на локальном компьютере.
  
   ![gif](https://github.com/IvanAnokhin/GifApplication/assets/119599047/5d91d577-2b23-4ebf-916a-f254d4a9d322)
### Как использовать
* Установка и запуск: Для начала работы с приложением выполните следующие шаги:
Склонируйте репозиторий на свой компьютер с помощью команды:
* git clone https://github.com/yourusername/gif-search-application.git
* Убедитесь, что у вас установлен Java Development Kit (JDK).
* Получите API ключ от Giphy, зарегистрировавшись на https://developers.giphy.com/. Замените значение API_KEY в файле GifApplication.java на свой API ключ.
* Откройте терминал (или командную строку) и перейдите в корневой каталог проекта (gif-search-application).
* Скомпилируйте и запустите приложение с помощью следующих команд: javac GifApplication.java / java GifApplication


### Интерфейс приложения: 
* При запуске приложения откроется окно с графическим интерфейсом, состоящим из нескольких элементов:
* Поле ввода для поиска GIF-изображений: Введите слово или фразу на английском языке, которое будет использоваться для поиска GIF.
* Кнопка "Поиск": Нажмите на эту кнопку или нажмите клавишу Enter, чтобы выполнить поиск GIF-изображений на основе введенного запроса.
* Область прокрутки с GIF-изображениями: В этой области будут отображены случайные GIF-изображения, соответствующие вашему запросу.
* Метка для сообщений: Здесь будут выводиться различные сообщения, связанные с результатами поиска и сохранения GIF.
* Поиск GIF: Чтобы выполнить поиск GIF-изображений, выполните следующие шаги:
* Введите слово или фразу на английском языке в поле ввода для поиска.
* Нажмите кнопку "Поиск" или нажмите клавишу Enter.
* Просмотр результатов: После выполнения поиска случайные GIF-изображения, связанные с вашим запросом, будут отображены в области прокрутки.
* Сохранение GIF: Чтобы сохранить выбранный GIF-изображение, выполните следующие действия:
* Найдите GIF-изображение, которое вы хотите сохранить, в области прокрутки.
* Каждое GIF-изображение сопровождается кнопкой "Save" (Сохранить) под ним. Нажмите на кнопку "Save" для сохранения GIF.
* Откроется диалоговое окно для выбора места сохранения GIF-изображения на вашем компьютере. Выберите папку и имя файла для сохранения и нажмите "Сохранить".

 ![gif2](https://github.com/IvanAnokhin/GifApplication/assets/119599047/c80561b7-3584-46e4-a4c6-f1b53025ba22)
### Дополнительные примечания
* Максимальное количество отображаемых GIF-изображений по умолчанию ограничено 10. Вы можете изменить это значение, изменив параметр limit в методе performSearch().
* Приложение отображает случайные GIF-изображения из результатов поиска. Вы можете изменить это поведение или добавить дополнительные опции поиска, если требуется.

### Компоненты приложения
* GifApplication.java: Главный класс Java, содержащий логику приложения, компоненты графического интерфейса (GUI) и взаимодействие с API Giphy.
* API_BASE_URL: Базовый URL для API Giphy. Здесь выполняются запросы поиска GIF.
* API_KEY: Ваш API ключ Giphy. Замените его на свой собственный, чтобы получить доступ к API Giphy.
* start(): Метод, который настраивает графический пользовательский интерфейс (GUI) и инициализирует основные компоненты.
* performSearch(): Обрабатывает функциональность поиска. Он отправляет запрос к API Giphy с заданным поисковым запросом и получает список URL-адресов GIF.
* saveGIF(): Сохраняет выбранный GIF в локальный файл на компьютере пользователя.
* hideInstruction(): Удаляет инструкции из сцены после выполнения поиска.
* showContextMenu(): Обрабатывает функциональность контекстного меню (щелчок правой кнопкой мыши), но в текущей версии приложения она не используется.
* searchGIF(): Отправляет запрос к API Giphy и получает список URL-адресов GIF на основе поискового запроса.

## Приятного поиска GIF! 😄🎉
