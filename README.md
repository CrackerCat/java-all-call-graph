[![Maven Central](https://img.shields.io/maven-central/v/com.github.adrninistrator/java-all-call-graph.svg)](https://search.maven.org/artifact/com.github.adrninistrator/java-all-call-graph/)

[![Apache License 2.0](https://img.shields.io/badge/license-Apache%20License%202.0-green.svg)](https://github.com/Adrninistrator/java-all-call-graph/blob/master/LICENSE)

# 1. 前言

在很多场景下，如果能够生成Java代码中方法之间的调用链，是很有帮助的，例如分析代码执行流程、确认被修改代码的影响范围、代码审计/漏洞分析等。

IDEA提供了显示调用指定Java方法向上的完整调用链的功能，可以通过“Navigate -> Call Hierarchy”菜单(快捷键：Ctrl+Alt+H)使用；Eclipse也提供了相同的功能。但以上都需要针对每个方法进行手工处理，不支持对方法进行过滤或者其他扩展功能。

以下实现了一个工具，能够批量生成指定Java方法向下的完整调用链，对于关注的Java方法，能够生成其向下调用的方法信息，及被调用方法再向下调用的方法，直到最下层被调用的方法。

也可以生成调用指定Java类方法向上的完整调用链，对于关注的Java类的方法，能够生成调用对应方法的方法信息，及调用上述方法的信息，直到最上层未被其他方法调用的方法（通常是对外提供的服务，或定时任务等）。

该工具生成的Java方法完整调用链中，支持显示相关的包名、类名、方法名、方法参数、调用者源代码行号、方法注解、循环调用，入口方法。

该工具支持生成某个方法到起始方法之间的调用链，也支持根据关键字查找关注的方法，生成其到起即方法之间的调用链。

`当前项目提供了插件功能，可用于为Java代码自动生成UML时序图`，可参考[https://github.com/Adrninistrator/gen-java-code-uml-sequence-diagram](https://github.com/Adrninistrator/gen-java-code-uml-sequence-diagram)。

# 2. 更新说明

## 2.1. (0.6.3)

- 支持使用本地文件数据库

支持使用本地文件形式的H2数据库，可不依赖外部的其他数据库，可以无法连接其他数据库（如MySQL）的环境中运行

H2数据库使用说明可参考[https://blog.csdn.net/a82514921/article/details/108029222](https://blog.csdn.net/a82514921/article/details/108029222)

当前工具生成的H2数据库中，schema为“jacg”

- 支持对目录进行处理

除了支持对jar/war包进行处理外，也支持对目录中的class、jar/war文件进行处理

支持指定一个或多个jar/war包，或一个或多个目录，或jar/war包与目录混合进行处理

该功能在java-callgraph2中实现，通过当前工具的config.properties配置文件中的call.graph.jar.list参数进行配置

可参考[https://github.com/Adrninistrator/java-callgraph2](https://github.com/Adrninistrator/java-callgraph2)

- 支持插件功能

提供用于生成Java方法UML时序图的插件功能

# 3. 输出结果示例

## 3.1. 调用指定类方法向上的完整调用链示例

调用指定类方法向上的完整调用链如下所示：

```
[0]#DestClass.destfunc()
[1]#  ClassA3.funcA3()	(ClassA3:10)
[2]#    ClassA2.funcA2()	(ClassA2:19)
[3]#      ClassA1.funcA1()	(ClassA1:23)    !entry!
[1]#  ClassB1.funcB1()	(ClassB1:57)    !entry!
[1]#  ClassC2.funcC2()	(ClassC2:31)
[2]#    ClassC1.funcC1()	(ClassC1:9)    !entry!
```

以上对应的调用关系如下所示：

![](pic/example-cg-4callee.png)

调用指定类方法向上的完整调用链输出结果格式类似一棵树，每行代表一个调用者Java方法，与实际的代码执行顺序无关，前面的数字越大代表调用层级越靠上，0代表被调用的指定类中的方法。

每行后部的“(TestClass:39)”格式的类名及数字代表当前调用者类名，及调用者方法对应的源代码行号。

对于不被其他方法调用的方法，认为是入口方法，在对应行的最后会显示“!entry!”。

以下为使用该工具生成的调用Mybatis的MyBatisExceptionTranslator类的部分方法向上完整调用链（方法参数太长，已省略）：

![](pic/example-callgraph-4callee.png)

`IDEA使用技巧：在IntelliJ IDEA中，打开“Navigate Class...”窗口，即根据类名进入对应代码文件的窗口后，若输入[类名]:[行号]格式的内容并回车，可打开对应的代码文件并跳转到对应的行号。`

## 3.2. 指定方法向下完整调用链示例

指定方法向下完整调用链如下所示：

```
[0]#SrcClass.srcfunc()
[1]#  [SrcClass:15]	ClassA1.funcA1()
[2]#    [ClassA1:27]	ClassA2a.funcA2a()
[2]#    [ClassA1:59]	ClassA2b.funcA2b()
[3]#      [ClassA2b:39]	ClassA3.funcA3()
[1]#  [SrcClass:17]	ClassB1.funcB1()
[1]#  [SrcClass:23]	ClassC1.funcC1()
[2]#    [ClassC1:75]	ClassC2.funcC2()
```

以上对应的调用关系如下所示：

![](pic/example-cg-4caller.png)

指定方法向下完整调用链输出结果类似一棵树，每行代表一个被调用者Java方法，与实际的代码执行顺序一致，前面的数字越大代表调用层级越靠下，0代表指定方法。

每行前部的“\[TestClass:39\]”格式的类名及数字，代表当前调用者类名，及调用者方法及对应的源代码行号。

以下为使用该工具生成的Mybatis的BatchExecutor:doUpdate()方法向下的完整调用链：

![](pic/example-callgraph-4caller.png)

除此之外，当方法指定了注解时，也可以显示在结果中，格式为“@xxx”；

当出现方法循环调用时，会显示出现循环调用的方法，格式为“!cycle\[x\]!”。

# 4. 使用说明

## 4.1. 依赖环境

该工具将Java方法调用关系写入文件之后，会将数据保存在数据库中，需要访问MySQL或H2数据库（理论上支持其他数据库，但可能需要对SQL语句进行调整）。

`建议使用本地文件形式的H2数据库`，可不依赖外部的其他数据库，使用更简单；且经过简单测试，H2比MySQL数据库的读写速度更快

所使用的数据库用户需要有DML读写权限，及DDL权限（需要执行CREATE TABLE、TRUNCATE TABLE操作）。

## 4.2. 引入组件

在使用该工具前，首先需要在对应的项目引入该工具组件的依赖，将其引入到test模块或使用provided类型，可以避免发布到服务器中。

- Gradle

```
testImplementation 'com.github.adrninistrator:java-all-call-graph:0.6.3'
```

- Maven

```xml
<dependency>
  <groupId>com.github.adrninistrator</groupId>
  <artifactId>java-all-call-graph</artifactId>
  <version>0.6.3</version>
</dependency>
```

最新版本号可查看[https://search.maven.org/artifact/com.github.adrninistrator/java-all-call-graph](https://search.maven.org/artifact/com.github.adrninistrator/java-all-call-graph)。

对应代码地址为[https://github.com/Adrninistrator/java-all-call-graph](https://github.com/Adrninistrator/java-all-call-graph)。

建议在需要生成方法调用链的项目中分别引入依赖，可以使每个项目使用单独的配置，不会相互影响。

该工具仅引入了log4j-over-slf4j组件，在引入该工具组件的项目中，还需要引入log4j2、logback等日志组件，且保证配置正确，能够在本地正常运行。

## 4.3. 执行步骤

以下所述执行步骤，需要在IDE中执行。

### 4.3.1. 总体步骤

该工具的总体使用步骤如下：

- a. 将后续步骤使用的几个启动类对应的Java文件，及配置文件解压到当前Java项目的test模块的对应目录中，该步骤只需要执行一次（当组件更新时需要再次执行，以释放新的文件）；
- b. 调用增强后的java-callgraph2.jar（详细内容见后续“原理说明”部分），解析指定jar包中的class文件，将Java方法调用关系写入文件；从该文件读取Java方法调用关系，再写入MySQL数据库；
- c.1 需要生成调用指定类的向上完整方法调用链时，从数据库读取方法调用关系，再将完整的方法调用链写入文件；
- c.2 需要生成指定方法的向下完整方法调用链时，从数据库读取方法调用关系，再将完整的方法调用链写入文件；

如下图所示：

![](pic/step-all.png)

### 4.3.2. 释放启动类及配置文件

当前步骤在每个Java项目只需要执行一次。

`当组件升级后，若对配置文件有新增或修改，则需要再执行当前步骤，否则可能会因为缺少配置文件导致执行失败。`

执行当前步骤时，需要执行main()方法的类名如下：

```
com.adrninistrator.jacg.unzip.UnzipFile
```

需要选择classpath对应模块为test。

执行以上类后，会将java-all-callgraph.jar中保存配置文件的~jacg_config、~jacg_extensions、~jacg_find_keyword、~jacg_sql目录，保存启动类（下文涉及的Test...类）的“test/jacg”目录，分别释放到当前Java项目的test模块的resources、java目录中（仅在本地生效，避免发布到服务器中）。

若当前Java项目存在“src/test”或“src/unit.test”目录，则将配置文件与Java文件分别释放在该目录的resources、java目录中；

若当前Java项目不存在以上目录，则将上述文件释放在“~jacg-\[当前时间戳\]”目录中，之后需要手工将对应目录拷贝至test模块对应目录中。

当目标文件不存在时，则会进行释放；若目标文件已存在，则不会覆盖。

### 4.3.3. 生成Java方法调用关系并写入数据库

在生成Java方法调用关系并写入数据库之前，需要确保需要分析的jar包或war包已存在，对于使用源码通过构建工具生成的jar/war包，或者Maven仓库中的jar包，均可支持（需要是包含.class文件的jar包）。

`当需要解析的jar/war包中的class文件内容发生变化时，需要重新执行当前步骤，以重新获取对应jar/war包中的Java方法调用关系，写入文件及数据库；若需要解析的jar/war包文件未发生变化，则不需要重新执行当前步骤。`

在后续生成Java方法完整调用链时，若发现指定的jar包未入库，或内容发生了改变，则工具会提示需要先执行当前步骤生成方法调用关系并入库。

执行当前步骤时，需要执行main()方法的类名如下：

```
test.jacg.TestRunnerWriteDb
```

需要选择classpath对应模块为test。

当前步骤执行的操作及使用的相关参数如下图所示：

![](pic/args-use-b.png)

- b.1 调用增强后的java-callgraph2.jar中的类的方法

TestRunnerWriteDb类读取配置文件`~jacg_config/config.properties`中的参数：

`call.graph.jar.list`：等待解析的jar包，或保存class文件目录路径列表，各jar包路径之间使用空格分隔（若路径中包含空格，则需要使用""包含对应的路径）

将第1个jar包路径后面加上“.txt”作为本次保存Java方法调用关系文件路径；

设置JVM参数“output.file”值为本次保存Java方法调用关系文件的路径，调用增强后的java-callgraph2.jar中的类的方法，通过方法的参数传递上述jar包路径列表；

- b.2 解析指定jar包

增强后的java-callgraph2.jar中的类的方法开始解析指定的jar包；

- b.3 将Java方法调用关系写入文件

增强后的java-callgraph2.jar中的类的方法将解析出的Java方法调用关系写入指定的文件中；

- b.4 读取Java方法调用关系文件

TestRunnerWriteDb类读取保存Java方法调用关系的文件，文件路径即第1个jar包路径加“.txt”；

- b.5 将Java方法调用关系写入数据库

TestRunnerWriteDb类读取配置文件`~jacg_config/i_allowed_class_prefix.properties`，该文件中指定了需要处理的类名前缀，可指定包名，或包名+类名，示例如下：

```
com.test
com.test.Test1
```

读取配置文件`~jacg_config/config.properties`中的参数：

`app.name`：当前应用名称，对应数据库表名后缀，该参数值中的分隔符不能使用“-”，需要使用“_”

`thread.num`：写入数据库时并发处理的线程数量，也是数据源连接池数量

`db.use.h2`：是否使用H2数据库，值为true/false；当开关为开时，还需要配置db.h2.file.path参数，当开关为关时，还需要配置db.driver.name、db.url、db.username、db.password参数

`db.h2.file.path`：H2数据库文件路径（仅当使用H2数据库时需要指定），示例：./build/jacg_h2db，不需要指定“.mv.db”

`db.driver.name`：数据库驱动类名（仅当使用非H2数据库时需要指定）

`db.url`：数据库JDBC URL（仅当使用非H2数据库时需要指定），使用MySQL时，url需要指定rewriteBatchedStatements=true，开启批量插入，提高效率

`db.username`：数据库用户名（仅当使用非H2数据库时需要指定）

`db.password`：数据库密码（仅当使用非H2数据库时需要指定）

`input.ignore.other.package`：忽略其他包的开关，值为true/false；当开关为开时，仅将`~jacg_config/i_allowed_class_prefix.properties`中指定的类名前缀相符的类调用关系写入数据库；当开关为关时，所有的类调用关系都写入数据库

向数据库写入数据库前，会判断对应数据库表是否存在，若不存在则创建，之后会执行“TRUNCATE TABLE”操作清空表中的数据；

根据配置文件`~jacg_config/config.properties`中的`input.ignore.other.package`参数值及配置文件`~jacg_config/i_allowed_class_prefix.properties`，将Java方法调用关系逐条写入数据库中；

增强后的java-callgraph2.jar除了会将Java方法调用关系写入文件外，还会将各个方法上的注解信息写入文件（文件名为保存方法调用关系的文件名加上“-annotation.txt”）；TestRunnerWriteDb类也会读取对应文件，将各方法上的注解信息写入数据库中。

### 4.3.4. 生成调用指定类方法向上的完整调用链

执行当前步骤之前，需要确认Java方法调用关系已成功写入数据库中。

执行当前步骤时，需要执行main()方法的类名如下：

```
test.jacg.TestRunnerGenAllGraph4Callee
```

需要选择classpath对应模块为test。

当前步骤执行的操作及使用的相关参数如下图所示：

![](pic/args-use-c.1.png)

- c.1.1 从数据库读取Java方法调用关系

TestRunnerGenAllGraph4Callee类读取配置文件`~jacg_config/o_g4callee_class_name.properties`，该文件中指定了需要生成向上完整调用链的类名；

类名可指定简单类名或完整类名；若存在同名类，则需要指定完整类名；

示例如下：

```
Test1
com.test.Test1
```

读取配置文件`~jacg_config/config.properties`中的参数：

`thread.num`：从数据库并发读取数据的线程数量，也是数据源连接池数量；若`~jacg_config/o_g4callee_class_name.properties`配置文件中的记录数比该值小，则会使用记录数覆盖该参数值

以下参数说明略：app.name、db.use.h2、db.h2.file.path、db.driver.name、db.url、db.username、db.password

- c.1.2 将方法完整调用链（向上）写入文件

对于配置文件`~jacg_config/o_g4callee_class_name.properties`中指定的类，对每个类生成一个对应的文件，文件名为“\[类名\].txt”，在某个类对应的文件中，会为对应类的每个方法生成向上完整调用链；

以上文件名示例为“TestClass1.txt”；

每次执行时会生成一个新的目录，用于保存输出文件，目录名格式为“~jacg_o_ee/\[yyyyMMdd-HHmmss.SSS\]”；

读取配置文件`~jacg_config/config.properties`中的参数：

`call.graph.output.detail`：输出文件中调用关系的详细程度，1: 最详细，包含完整类名+方法名+方法参数，2: 中等，包含完整类名+方法名,3: 最简单,包含简单类名（对于同名类展示完整类名）+方法名，示例如下

|call.graph.output.detail参数值|显示示例|
|---|---|
|1|com.test.Test1.func1(java.lang.String)|
|2|com.test.Test1.func1|
|3|Test1.func1|

`show.method.annotation`：调用链中是否显示方法上的注解开关，值为true/false；当开关为开时，会显示当前方法上的全部注解的完整类名，格式为“\[方法信息\]@注解1@注解2...”

`gen.combined.output`：是否生成调用链的合并文件开关，值为true/false；当开关为开时，在为各个类生成了对应的调用链文件后，会生成一个将全部文件合并的文件，文件名为“~all-4callee.txt”

`show.caller.line.num`：生成调用链时，是否需要显示调用者源代码行号开关，值为true/false；当开关为开时，会在向上的调用链每行后部显示当前调用者类名，及调用者方法对应的源代码行号，如“(TestClass:39)”

`gen.upwards.methods.file`：生成向上的调用链时，是否需要为每个方法生成单独的文件开关，值为true/false；当开关为开时，会为~jacg_config/o_g4callee_class_name.properties中指定的每个类的每个方法单独生成一个文件，保存在“~jacg_o_ee/\[yyyyMMdd-HHmmss.SSS\]/methods”目录中，文件名格式为“\[类名\]@\[方法名\]@\[完整方法名HASH+长度\].txt”

### 4.3.5. 生成指定方法向下完整调用链

执行当前步骤之前，需要确认Java方法调用关系已成功写入数据库中。

#### 4.3.5.1. 生成所有的调用链

执行当前步骤时，需要执行main()方法的类名如下：

```
test.jacg.TestRunnerGenAllGraph4Caller
```

需要选择classpath对应模块为test。

当前步骤执行的操作及使用的相关参数如下图所示：

![](pic/args-use-c.2.png)

- c.2.1 从数据库读取Java方法调用关系

TestRunnerGenAllGraph4Caller类读取配置文件`~jacg_config/o_g4caller_entry_method.properties`，该文件中指定了需要生成向下完整调用链的类名与方法名前缀，格式为\[类名\]:\[方法名\] \[起始代码行号\]-\[结束代码行号\]，或\[类名\]:\[方法名+参数\] \[起始代码行号\]-\[结束代码行号\]；

\[起始代码行号\]-\[结束代码行号\]为可选参数，若不指定则输出指定的整个方法向下的方法完整调用链，若指定则输出方法指定行号范围内向下的方法完整调用链，即 >= 起始代码行号 且 <= 结束代码行号的范围；

类名可指定简单类名或完整类名；若存在同名类，则需要指定完整类名；

示例如下：

```
Test1:func1 139-492
Test1:func1(
Test1:func1(java.lang.String)
com.test.Test1:func1 395-1358
com.test.Test1:func1(
com.test.Test1:func1(java.lang.String)
```

若`~jacg_config/o_g4caller_entry_method.properties`配置文件中指定的方法前缀对应多个方法，则可在`~jacg_config/o_g4caller_entry_method_ignore_prefix.properties`配置文件中指定需要忽略的方法前缀；

`~jacg_config/o_g4caller_entry_method_ignore_prefix.properties`配置文件的格式为方法名，或方法名+参数，示例如下：

```
func1
func1(
func1(java.lang.String)
```

例如指定生成Class1.test方法的向下完整调用链，存在方法Class1.test1，则可指定忽略test1方法；指定生成Class1.test方法的向下完整调用链，所关注的test方法为test(java.lang.String)，存在不关注的方法test(java.lang.Integer)，则可指定忽略test(java.lang.Integer)方法；

读取配置文件`~jacg_config/config.properties`中的参数：

`thread.num`：从数据库并发读取数据的线程数量，也是数据源连接池数量；若`~jacg_config/o_g4caller_entry_method.properties`配置文件中的记录数比该值小，则会使用记录数覆盖该参数值

以下参数说明略：app.name、db.use.h2、db.h2.file.path、db.driver.name、db.url、db.username、db.password

- c.2.2 将方法完整调用链（向下）写入文件

对于配置文件`~jacg_config/o_g4caller_entry_method.properties`中指定的方法，对每个方法生成一个对应的文件，文件名为“\[类名\]@\[方法名\]@\[完整方法名HASH+长度\].txt”，示例为“TestClass1@func1@qDb0chxHzmPj1F26S7kzhw#048.txt”；

若某个方法生成向下的完整调用链时指定了代码行号范围，则对应文件名为“\[类名\]@\[方法名\]@\[完整方法名HASH+长度\]@\[起始代码行号\]-\[结束代码行号\].txt”，示例为“TestClass1@func1@qDb0chxHzmPj1F26S7kzhw#048@135-395.txt”；

每次执行时会生成一个新的目录，用于保存输出文件，目录名格式为“~jacg_o_er/\[yyyyMMdd-HHmmss.SSS\]”；

读取配置文件`~jacg_config/config.properties`中的参数：

`gen.combined.output`：是否生成调用链的合并文件开关，值为true/false；当开关为开时，在为各个类生成了对应的调用链文件后，会生成一个将全部文件合并的文件，文件名为“~all-4caller.txt”

`show.caller.line.num`：生成调用链时，是否需要显示调用者源代码行号开关，值为true/false；当开关为开时，会在向下的调用链每行前部显示当前调用者类名，及调用者方法对应的源代码行号，如“\[TestClass:39\]”

以下参数说明略：call.graph.output.detail、show.method.annotation。

#### 4.3.5.2. 忽略特定的调用关系

以上生成指定方法向下的完整调用链中，包含了所有的方法调用链，可用于查找指定方法直接调用及间接调用的方法，例如通过调用的Mybatis的Mapper接口确认该方法相关的数据库表操作；

当生成指定方法向下的完整调用链是为了人工分析代码结构时，若包含了所有的方法调用链，则会有很多不重要的代码产生干扰，例如对dto、entity等对象的读取及赋值操作、通信数据序列化/反序列化操作（JSON等格式）、日期操作、流水号生成、请求字段格式检查、注解/枚举/常量/异常/日期相关类操作、Java对象默认方法调用等；

调用以下类，支持将不关注的方法调用关系忽略：

```
test.jacg.TestRunnerGenAllGraph4CallerSupportIgnore
```

在配置文件`~jacg_config/o_g4caller_ignore_class_keyword.properties`中可以指定需要忽略的类名关键字，可为包名中的关键字，或类名中的关键字，示例如下：

```
.dto.
.entity.
Enum
Constant
```

在配置文件`~jacg_config/o_g4caller_ignore_full_method_prefix.properties`中可以指定需要忽略的完整方法前缀，可指定包名，或包名+类名，或包名+类名+方法名，或包名+类名+方法名+参数，示例如下：

```
com.test
com.test.Test1
com.test.Test1:func1
com.test.Test1:func1(
com.test.Test1:func1(java.lang.String)
```

在配置文件`~jacg_config/o_g4caller_ignore_method_prefix.properties`中可以指定需要忽略的方法名前缀，如Java对象中的默认方法“toString()、hashCode()、equals(java.lang.Object)、\<init\>(、\<clinit\>(”等，示例如下：

```
func1
func1( 
func1()
func1(java.lang.String)
```

## 4.4. 使用命令行方式执行

以上所述执行方式，需要在IDE中执行，假如需要使用命令行方式执行，可参考以下方法。

在项目根目录执行`gradlew gen_run_jar`命令，生成可以直接执行的jar包，并拷贝相关文件。

在生成的`output_dir`目录中，包含了当前项目生成的jar包、依赖jar包，以及资源文件、启动脚本等，如下所示：

```
~jacg_config
~jacg_extensions
~jacg_find_keyword
~jacg_sql
jar
lib
run.bat
run.sh
```

可选择run.bat或run.sh脚本，以命令行方式执行，脚本中执行的类可为test.jacg包中的类，可选择的类可参考前文内容。

在执行脚本前，需要根据需要修改脚本中执行的类名。

# 5. 其他功能

## 5.1. 生成某个方法到起始方法之间的调用链

该工具生成的向上或向下的Java方法完整调用链内容通常会比较多，如果只关注某个方法到起始方法之间的调用链时，可以按照以下步骤生成：

执行以下java类：

|完整类名|说明|
|---|---|
|test.jacg.TestGenSingleCallGraph4ee|处理向上的完整调用链文件，按照层级减小的方向显示|
|test.jacg.TestGenSingleCallGraph4er|处理向下的完整调用链文件，按照层级增大的方向显示|

需要选择classpath对应模块为test。

在程序参数（即main()方法处理的参数）中指定对应的向上或向下的Java方法完整调用链文件路径，及关注的方法所在行号，支持批量查询，格式为“\[完整调用链文件路径\] \[关注方法所在行号1\] \[关注方法所在行号2\] ... \[关注方法所在行号n\]”。

当文件路径包含空格时，需要使用""包含。

例如完整调用链文件“dir\\a.txt”内容如下：

|行号|内容|
|---|---|
|1|\[0\]#DestClass.destfunc()|
|2|\[1\]#  ClassA3.funcA3()	(ClassA3:10)|
|3|\[2\]#    ClassA2.funcA2()	(ClassA2:19)|
|4|\[3\]#      ClassA1.funcA1()	(ClassA1:23)    !entry!|
|5|\[1\]#  ClassB1.funcB1()	(ClassB1:57)    !entry!|
|6|\[1\]#  ClassC2.funcC2()	(ClassC2:31)|
|7|\[2\]#    ClassC1.funcC1()	(ClassC1:9)    !entry!|

假如希望知道第7行“\[2\]#    ClassC1.funcC1()	(ClassC1:9)    !entry!”方法到起始方法“\[0\]#DestClass.destfunc()”之间的调用关系，可在执行以上类时指定程序参数为“dir\\a.txt 7”，则生成调用关系如下：

```
# 行号: 7
[0]#DestClass.destfunc()
[1]#  ClassC2.funcC2()	(ClassC2:31)
[2]#    ClassC1.funcC1()	(ClassC1:9)    !entry!
```

*以上功能通常不需要使用，可以使用下述功能代替。*

## 5.2. 生成包含关键字的所有方法到起始方法之间的调用链

如果需要生成包含关键字的所有方法到起始方法之间的调用链，例如获得入口方法到被调用的起始方法之间的调用链，或起始方法到Mybatis的Mapper之间的调用链等场景，可以按照以下步骤生成：

执行以下java类：

|完整类名|说明|
|---|---|
|test.jacg.TestFindKeywordCallGraph4ee|处理向上的完整调用链文件，按照层级减小的方向显示|
|test.jacg.TestFindKeywordCallGraph4er|处理向下的完整调用链文件，按照层级增大的方向显示|

以上类在执行时支持不指定程序参数（即main()方法处理的参数），或指定程序参数，建议使用不指定程序参数的方式。

- 不指定程序参数

执行以上类时不指定程序参数，则会先生成对应的向上（或向下）方法完整调用链，再对生成目录的文件根据关键字生成到起始方法的调用链。

执行TestFindKeywordCallGraph4ee类时，关键字在配置文件“~jacg_find_keyword/find_keyword_4callee.properties”中指定；执行TestFindKeywordCallGraph4er类时，关键字在配置文件“~jacg_find_keyword/find_keyword_4caller.properties”中指定。

- 指定程序参数

在程序参数中指定对应的向上或向下的Java方法完整调用链文件路径，及对应的关键字，支持批量查询，格式为“\[完整调用链文件路径/保存完整调用链文件的目录路径\] \[关键字1\] \[关键字2\] ... \[关键字n\]”。

- 生成结果示例

例如完整调用链文件“dir\\a.txt”内容如上所示。

假如希望知道包含关键字“!entry!”的所有方法到起始方法“\[0\]#DestClass.destfunc()”之间的调用关系，可在执行以上类时指定程序参数为“dir\\a.txt !entry!”，则生成调用关系如下：

```
# 行号: 4
[0]#DestClass.destfunc()
[1]#  ClassA3.funcA3()	(ClassA3:10)
[2]#    ClassA2.funcA2()	(ClassA2:19)
[3]#      ClassA1.funcA1()	(ClassA1:23)    !entry!

# 行号: 5
[0]#DestClass.destfunc()
[1]#  ClassB1.funcB1()	(ClassB1:57)    !entry!

# 行号: 7
[0]#DestClass.destfunc()
[1]#  ClassC2.funcC2()	(ClassC2:31)
[2]#    ClassC1.funcC1()	(ClassC1:9)    !entry!
```

以上功能也支持对保存完整调用链文件的目录进行处理，生成的文件保存在指定目录的“find_keyword_\[yyyyMMdd-HHmmss.SSS\]”子目录中。

## 5.3. 处理循环方法调用

在生成Java方法完整调用链时，若出现了循环方法调用，该工具会从循环调用中跳出，并在生成的方法调用链中对出现循环调用的方法增加标记“!cycle\[n\]!”，其中n代表被循环调用的方法对应层级。

生成向上的Java方法完整调用链时，出现循环方法调用的示例如下：

```
org.springframework.transaction.TransactionDefinition:getIsolationLevel()
[0]#org.springframework.transaction.TransactionDefinition:getIsolationLevel
[1]#  org.springframework.transaction.support.DelegatingTransactionDefinition:getIsolationLevel	(DelegatingTransactionDefinition:56)
[2]#    org.springframework.transaction.TransactionDefinition:getIsolationLevel	(TransactionDefinition:0)	!cycle[0]!
```

生成向下的Java方法完整调用链时，出现循环方法调用的示例如下：

```
org.springframework.transaction.support.TransactionTemplate:execute(org.springframework.transaction.support.TransactionCallback)
[0]#org.springframework.transaction.support.TransactionTemplate:execute
[1]#  [TransactionTemplate:127]	org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager:execute
[2]#    [CallbackPreferringPlatformTransactionManager:0]	org.springframework.transaction.jta.WebSphereUowTransactionManager:execute
[3]#      [WebSphereUowTransactionManager:225]	org.springframework.transaction.support.DefaultTransactionDefinition:<init>
[4]#        [DefaultTransactionDefinition:74]	java.lang.Object:<init>
[3]#      [WebSphereUowTransactionManager:228]	org.springframework.transaction.TransactionDefinition:getTimeout
[4]#        [TransactionDefinition:0]	org.springframework.transaction.support.DefaultTransactionDefinition:getTimeout
[4]#        [TransactionDefinition:0]	org.springframework.transaction.support.DelegatingTransactionDefinition:getTimeout
[5]#          [DelegatingTransactionDefinition:61]	org.springframework.transaction.TransactionDefinition:getTimeout	!cycle[3]!
```

# 6. 分析脚本

在[https://github.com/Adrninistrator/java-all-call-graph](https://github.com/Adrninistrator/java-all-call-graph)的“shell脚本”、“SQL语句”目录中，保存了一些脚本，可以用于对方法完整调用链进行一些分析操作，包含shell脚本与SQL语句。

# 7. 原理说明

## 7.1. Java方法调用关系获取

在获取Java方法调用关系时，参考了 [https://github.com/gousiosg/java-callgraph](https://github.com/gousiosg/java-callgraph) 项目，使用Apache Commons BCEL（Byte Code Engineering Library）解析Java方法调用关系。

原始java-callgraph在多数场景下能够获取到Java方法调用关系，但存在一些场景调用关系会缺失。

针对调用关系缺失的问题，在java-callgraph2项目中进行了优化和其他功能的增强，地址为[https://github.com/Adrninistrator/java-callgraph2](https://github.com/Adrninistrator/java-callgraph2)。

java-callgraph2能够生成缺失的调用关系。对于更复杂的情况，例如存在接口Interface1，及其抽象实现类Abstract1，及其子类ChildImpl1，若在某个类中引入了抽象实现类Abstract1并调用其方法的情况，生成的方法调用关系中也不会出现缺失。

原始java-callgraph缺失的调用关系如下所示：

- 接口与实现类方法

假如存在接口Interface1，及其实现类Impl1，若在某个类Class1中引入了接口Interface1，实际为实现类Impl1的实例（使用Spring时的常见场景），在其方法Class1.func1()中调用了Interface1.fi()方法；

原始java-callgraph生成的方法调用关系中，只包含Class1.func1()调用Interface1.fi()的关系，Class1.func1()调用Impl1.fi()，及Impl1.fi()向下调用的关系会缺失。

- Runnable实现类线程调用

假如f1()方法中使用内部匿名类形式的Runnable实现类在线程中执行操作，在线程中执行了f2()方法，如下所示：

```java
private void f1() {
    new Thread(new Runnable() {
        @Override
        public void run() {
            f2();
        }
    }).start();
}
```

原始java-callgraph生成的方法调用关系中，f1()调用f2()，及f2()向下调用的关系会缺失；

对于使用命名类形式的Runnable实现类在线程中执行操作的情况，存在相同的问题，原方法调用线程中执行的方法，及继续向下的调用关系会缺失。

- Callable实现类线程调用

与Runnable实现类线程调用情况类似，略。

- Thread子类线程调用

与Runnable实现类线程调用情况类似，略。

- lambda表达式（含线程调用等）

假如f1()方法中使用lambda表达式的形式在线程中执行操作，在线程中执行了f2()方法，如下所示：

```java
private void f1() {
    new Thread(() -> f2()).start();
}
```

原始java-callgraph生成的方法调用关系中，f1()调用f2()，及f2()向下调用的关系会缺失；

对于其他使用lambda表达式的情况，存在相同的问题，原方法调用lambda表达式中执行的方法，及继续向下的调用关系会缺失。

- Stream调用

在使用Stream时，通过xxx::func方式调用方法，原始java-callgraph生成的方法调用关系中会缺失。如以下示例中，当前方法调用当前类的map2()、filter2()，及TestDto1类的getStr()方法的调用关系会缺失。

```java
list.stream().map(this::map2).filter(this::filter2).collect(Collectors.toList());
list.stream().map(TestDto1::getStr).collect(Collectors.toList());
```

- 父类调用子类的实现方法

假如存在抽象父类Abstract1，及其非抽象子类ChildImpl1，若在某个类Class1中引入了抽象父类Abstract1，实际为子类ChildImpl1的实例（使用Spring时的常见场景），在其方法Class1.func1()中调用了Abstract1.fa()方法；

原始java-callgraph生成的方法调用关系中，只包含Class1.func1()调用Abstract1.fa()的关系，Class1.func1()调用ChildImpl1.fa()的关系会缺失。

- 子类调用父类的实现方法

假如存在抽象父类Abstract1，及其非抽象子类ChildImpl1，若在ChildImpl1.fc1()方法中调用了父类Abstract1实现的方法fi()；

原始java-callgraph生成的方法调用关系中，ChildImpl1.fc1()调用Abstract1.fi()的关系会缺失。

## 7.2. Java方法完整调用链生成

- 数据库表

在获取了Java方法调用关系之后，将其保存在数据库中，可查看java-all-callgraph.jar释放的~jacg_sql目录中的.sql文件，相关数据库表如下所示：

|表名前缀|注释|作用|
|---|---|---|
|class\_name\_|类名信息表|保存相关类的完整类名及简单类名|
|method\_annotation\_|方法注解表|保存方法及方法上的注解信息|
|method\_call\_|方法调用关系表|保存各方法之间调用信息|
|jar\_info\_|jar包信息表|保存用于解析方法调用关系的jar包信息|
|extended\_data\_|自定义数据表|
|manual\_add\_extended\_data\_|手工添加的自定义数据表|

上述数据库表在创建时使用表名前缀加上配置文件`~jacg_config/config.properties`中的`app.name`参数值。

该工具会主要从方法调用关系表中逐级查询数据，生成完整的方法调用链。

- 禁用sql_mode中的ONLY_FULL_GROUP_BY

在MySQL 5.7中，执行类似以下SQL语句时，使用默认配置会出现以下报错：

```sql
select distinct(callee_method_hash),callee_full_method from method_call_xxx where callee_class_name= 'xxx' order by callee_method_name
```

```
Expression #1 of ORDER BY clause is not in SELECT list, references column 'xxxx' which is not in SELECT list; this is incompatible with DISTINCT
```

说明可见[https://dev.mysql.com/doc/refman/5.7/en/sql-mode.html](https://dev.mysql.com/doc/refman/5.7/en/sql-mode.html)。

为了使MySQL支持以上查询语句，需要禁用sql_mode中的ONLY_FULL_GROUP_BY，该工具会在查询时自动禁用。

# 8. 无法正确处理的情况

以下情况，对应的方法找不到被调用关系，可能会被误识别为入口方法：

- 不是直接通过Java方法进行调用的情况（例如在XML文件中配置代码执行流程、通过注解配置代码执行流程、使用AOP处理等）；
- 未被调用的方法。

# 9. 多余的调用关系处理

## 9.1. 问题

当代码中引入了接口或抽象父类，且对应多个实现类或子类时，生成的方法完整调用链可能存在多余的调用关系。

当一个接口对应多个实现类时，若在某个类中引入了接口，并调用其方法，生成的完整调用链中，可能将当前类未使用的其他实现类相关的调用关系也包含进来；

当一个抽象父类对应多个非抽象子类时，若在某个类中引入了抽象父类，并调用其方法，生成的完整调用链中，可能将当前类未使用的其他非抽象子类相关的调用关系也包含进来。

当代码中使用工厂模式获取某个接口/抽象父类的实现类/非抽象子类时，也可能会出现类似的问题。

## 9.2. 解决

当存在以上情况时，该工具会在当前目录生成“~notice_multi_ITF.md”或“~notice_multi_SCC.md”文件，可按照文档中的提示，将前缀为“method\_call\_”的数据库表中不需要的方法调用设置为禁用。

当需要将禁用的方法调用恢复为启用时，可按照当前目录生成的“~notice_disabled_ITF.md”或“~notice_disabled_SCC.md”文件的说明进行操作。

# 10. 适用场景

## 10.1. 分析代码执行流程

使用该工具生成指定方法向下调用链的功能，可以将代码中复杂的方法调用转换为相对简单的方法调用链形式展示。

人工查看生成的调用链时，能够通过类名及方法名识别出对应含义。

支持将不关注的方法调用忽略，仅展示重要的方法调用。

对于分析代码执行流程有一定帮助，适合梳理交易流程、首次接触代码时熟悉流程等场景。

## 10.2. 确认被修改代码的影响范围

使用该工具生成指定方法向上调用链的功能，可以生成调用指定类的所有方法的调用链。

能识别入口方法，减少人工逐层确认入口方法的工作量。

可用于快速确认被修改代码的影响范围。

## 10.3. 应用功能拆分

在进行应用功能拆分时，需要准确定位指定功能涉及的数据库表，及使用了对应数据库表的相关入口方法。

使用该工具生成指定方法向下调用链的功能，生成指定入口方法向下的调用链，能够根据类的包名快速找到Mapper接口（使用Mybatis的场景），即可找到相关的数据库表。

使用该工具生成指定方法向上调用链的功能，生成调用指定Mapper接口向上的调用链，能够根据“!entry!”找到入口方法。

重复执行以上过程，直到没有再找到新的Mapper接口（即数据库表）和入口方法，即可确认指定功能涉及的数据库表及相关入口方法。

## 10.4. 代码审计/漏洞分析

在进行代码审计时，可使用该工具梳理交易流程，生成指定方法向下的调用链，查找是否有调用敏感API；或者生成指定方法向上的调用链，查找调用敏感API的场景。

在进行漏洞分析时，结合该工具生成的完整调用链辅助分析，也能提高效率。
