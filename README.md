# trains
 
Рассматривается линейный участок железной дороги, соединяющий N станций (7≤ N ≤ 20). Известно суточное расписание движения электропоездов между этими станциями (в одном направлении), которое включает M маршрутов (5≤ M ≤20). Каждый маршрут фиксирует:
- станцию-пункт отправления и станцию-пункт назначения;
- промежуточные станции маршрута, в которых электропоезд делает остановку;
- время прибытия и отправки электропоезда в каждой станции маршрута. 

Фактическое движение электропоездов зависит не только от расписания, но и от некоторых непредвиденных событий, к числу которых относятся задержки поездов на станциях маршрута, а также аварии поездов и повреждения железнодорожных путей (которые на некоторое время нарушают движение).

Требуется разработать систему контроля движения электропоездов, которая отслеживает их движение по маршрутам, регистрирует возникающие события и отклонения движения от расписания, а также корректирует при необходимости расписание, определяя время предполагаемого прибытия поездов на каждую станцию маршрута. Можно считать, что электропоезда двигаются по маршрутам с определенной скоростью (например, 70 км/час).

Цель моделирования – изучение стабильности движения поездов при заданном расписании движения в условиях возникающих непредвиденных событий. Период моделирования – один день.
Случайные факторы движения (задержки и аварии) следует моделировать статистически, определив для каждого фактора свой вероятностный закон распределения. Интервал времени между возникновением двух непредвиденных событий – случайная величина, изменяющаяся в некотором диапазоне (например, для аварий – от 2 до 15 часов). Случайными величинами являются также длительность ремонта после аварии и время задержки при остановках электропоезда на станции.

Кроме величин N, M и расписания движения, в изменяемые параметры моделирования целесообразно включить: время суток начала моделирования движения электропоездов, шаг моделирования – 15 или 30 минут, диапазон изменения указанных случайных величин (от диапазона зависит частота возникновения непредвиденных событий). 

В ходе моделирования на экране компьютера должна быть изображена схема рассматриваемого участка железной дороги, на которой показано движение поездов в соответствии с расписанием, возникающие аварии и неисправности путей. По запросу необходимо также показать расписание движения, скорректированное в соответствии с уже случившимися событиями. По окончании моделирования должен быть выведен график смоделированного движения поездов, подсчитанная средняя задержка на станциях, общее время нарушения движения вследствие аварии. 

Полезной функцией создаваемой системы контроля является введение и расчет дополнительного маршрута. При этом система по заданному времени отправления поезда, заданным станциям отправления и назначения должна определить расписание движения по новому маршруту (по возможности, без изменений старых маршрутов). Время остановки электропоезда на промежуточных станциях должно быть в интервале от 2 до 5 минут. 

Возможное усложнение задачи – рассмотреть движение электропоездов на рассматриваемом участке дороги в обоих направлениях (по каждому направлению действует свое расписание). При этом авария поезда нарушает движение только в одну сторону, а неисправность железнодорожного пути – движение в обоих направлениях.
