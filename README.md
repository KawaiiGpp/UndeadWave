# Undead Wave 亡灵浪潮
末日到来，僵尸、骷髅等怪物从黑暗处不断涌出，发出刺耳的哀嚎。  
在被它们抓住之前，快拿起手中的武器，尽一切努力求生。  
本插件基于`Spigot API 1.20`开发，使用`AkiraCore`作为依赖。


## 玩法介绍
击杀你所见到的每一个怪物，收集他们掉落的硬币。  
在地图中找到命令方块，用硬币购买更强大的武器和道具。  
在一轮轮的怪物浪潮中存活到最后，你便是赢家。  
<br>
游戏内置了大量武器类型，满足远程与近战的不同战斗方式。  
推荐回合数为`15回合`（默认配置），足以刷出所有类型的怪物。  


## 游戏指令
指令分为`管理员指令`与`玩家指令`。  
### 管理员指令：
- `/undeadwaveadmin enable` 启用游戏，玩家将可以加入游戏；
- `/undeadwaveadmin disable` 禁用游戏，玩家将不可加入；
- `/undeadwaveadmin location <位置>` 设置游戏内坐标信息；
- `/undeadwaveadmin monsterpoint <操作>` 设置怪物刷新点的坐标；
- `/undeadwaveadmin help` 查看所有管理员指令。
### 玩家指令
- `/undeadwave join` 加入游戏；
- `/undeadwave quit` 退出游戏；
- `/undeadwave help` 查看所有玩家指令。


## 配置过程
第一次使用插件时，你需要进行以下操作：
1. 把插件的`jar`文件放入`plugins`文件夹，并启动一次服务器。
2. 关闭服务器，对插件配置文件`config.yml`进行配置，并启动服务器。
3. 使用`/undeadwaveadmin`设置怪物刷新点/出生点/大厅等。
4. 使用`/undeadwaveadmin enable`让游戏投入使用。
5. 让玩家使用`/undeadwave join`加入游戏，开始游玩。

当你需要中途修改游戏内的位置坐标（如出生点），  
需先使用`/undeadwaveadmin disable`禁用游戏，修改后再启用。  