# PsPainterGraphCore
Класс управляет геометрическими/числовыми параметрами отрисовки одного графика.
График отрисовывается внутри области отрисовки, определяемой переменными areaX,areaY,areaW,areaH.

```java
public double []y;                  график определяется массивом

public int begin, end;              точки (включительно) определяют часть
                                    массива, которая подлежит отрисовке

public double xstep,ystep;          шаг между соседними отсчётами по x и y
                                    в пикселях

public double zeroLevel;            уровень, который считается нулём

public boolean autofillY = true;    параметр определяет должен ли ystep
                                    вычисляться автоматически

public int areaX,areaY,areaW,areaH; область отрисовки графика

public boolean ready = false;       переменная определяет, готов ли график
                                    к отрисовке

DrawLine drawLine;                  ф-ия drawLine интерфейса DrawLine
                                    используется при отрисовке
```
---
```java
public PsPainterGraphCore(double []y,int areaX,int areaY,int areaW,int areaH,DrawLine drawLine)
```
конструктор с минимальным набором параметров, вычисляет все недостающие параметры так, чтобы вписать весь график
в прямоугольник [areaX,areaY,areaW,areaH].
```java
private void calcParams()
```
вычисляет xstep для текущих begin и end;
если autofillY==true, обновляет ystep и zeroLevel;
```java
private boolean checkReady()
```
определяет, готов ли график к отрисовке

---
```java
public void setDrawLine(DrawLine drawLine)
```
задаёт способ отрисовки линии между двумя соседними точками - такими линиями будет отрисован весь график
```java
public void draw()
```
отрисовывает график - использует ф-ию void drawLine(double x1,double y1,double x2,double y2) интерфейса DrawLine
```java
public void refreshArea(int areaX,int areaY,int areaW,int areaH)
```
задаёт новую область отрисовки и пересчитывает под неё параметры
```java
public void zoomX(double rate,int point)
```
масштабирует по горизонтали путём изменения диапазона [begin:end] относительно point и пересчёта xstep
```java
public void zoomX(double rate)
```
масштабирует по горизонтали путём изменения диапазона [begin:end] относительно центра диапазона и пересчёта xstep
```java
public void fillYwhole()
```
вычисляет ystep и zeroLevel с учётом всех значений массива []y
```java
public void fillY()
```
вычисляет ystep и zeroLevel с учётом всех значений массива []y, лежащих в пределах [begin:end]
```java
public void calcXstep(int begin,int end)
```
вычисляет xstep под текущее значение areaW так, чтобы вписать часть графика [begin:end]
```java
public void calcXstep()
```
вычисляет xstep под текущее значение areaW так, чтобы вписать весь график
```java
public void fitFull()
```
полностью вписывает график в текущую область отрисовки - вычисляет xstep, ystep и zeroLevel
```java
public int getBeginX(double x)
```
возвращает номер отсёта массива []y, ближайшего слева от x
```java
public int getEndX(double x)
```
возвращает номер отсёта массива []y, ближайшего справа от x
```java
public void info()
```
тестовая ф-ия для отладки

---
```java
public interface DrawLine
```
интерфейс используется для определения способа отрисовки линии между соседними точками и соотв-но всего графика
