# Senla тестовое

## Для запуска использовать run.bat или run.sh
## Файл с аккаунтами(формат: номер_карты - пин - деньги - неправильные_попытки - дата_блокировки/0[0 - отсутствует]) был помещен в корень проекта и залит вместе с jar, чтобы не тратить лишние минуты на билд

## jar и файл с аккаунтами должны лежать на одном уровне вложенности(для простоты)

Intellij + Maven

Тесты ранятся под IDE - без готового скрипта(частично сделаны Jupiter + Mockito)

 - чтобы пинкод считался неправильным необходимо, чтобы он был в формате 4 цифр - иначе попытка не идет в счет непправильных(Пинкод 32345 - не считается увеличивающим кол-во попыток для бокировки, так как его формат неверен)
 - у банкомата есть захардкоженный лимит выдачи средств - нельзя снять больше, чем есть в банкомате
 - добавлена не сохраняемая(между сессиями) история транзакций с активной карты
