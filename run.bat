@echo off
::set PATH=.;D:\Program Files\Java\jdk1.8.0_191\jre\bin
::echo %PATH%
::set CLASSPATH=D:\Program Files\Java\jdk1.8.0_191\jre
::使用java命令就必须让cmd窗口pause,否则程序就会停止
::java -jar EyePro1.1.jar

::使用 start javaw命令就不用让cmd窗口pause
::-verbose:class就查看程序运行时类加载的过程，>> classpath1.txt就将类加载过程的输出写到文件中
::start javaw -jar -verbose:class EyePro1.1.jar >> classpath1.txt
start javaw -jar EyePro.jar
::@pause

REM cd EmptyRecycleBin
EmptyRecycleBin.exe /Q