## 一、打包方式
> 用idea Project structure -> artifacts -> + -> from module with dependencies

> jar files from libraries选copy to the output directory....

> directory for meta-inf/manifest.mf: 在选择好入口类后系统会将MANIFEST.MF默认放在src/main/java下（如果原来已生成需要先将其删除），需要将其放在项目下，去掉路径src/main/java，即xxxx\eyepro\下

> build菜单下build artifact

## 二、部署
> 1.将eyePro.db copy到jar包路径下

表结构：（原文件已有，不需要再执行）
```sql
CREATE TABLE "config" (
  "id" text,
  "break_time" TEXT,
  "popup_on" text,
  "popup_size" text,
  "notice_text" TEXT,
  "music_on" TEXT,
  "spvtts_on" TEXT,
  "spvtts_ch_on" TEXT,
  "freetts_on" TEXT,
  "mute" TEXT,
  "day_countdown" TEXT,
  "mission" TEXT,
  PRIMARY KEY ("id")
);

INSERT INTO "config"("id", "break_time", "popup_on", "popup_size", "notice_text", "music_on", "spvtts_on", "spvtts_ch_on", "freetts_on", "mute", "day_countdown", "mission") VALUES ('1', '36000', 'Y', '2.0', 'test', 'N', 'Y', 'N', 'Y', 'Y', '2019-12-31', 'test mission');

```
> 2.将打包出来的整个文件夹复制到运行目录
>
```
分别将
 jacob-xxx-x32.dll放在C:\Windows\System32
 jacob-xxx-x64.dll放在C:\Windows\SystemWoW64
 再将这两个放在jdk根目录\bin 
```
> 将music文件夹放到jar目录
> 将run.bat放到jar目录
> 使用javaProgramStart.bat运行
