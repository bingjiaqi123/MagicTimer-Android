# MagicTimer-Android

## 功能介绍

不需要注册/登录，数据全存在本地。

### 抽签功能

点击首页的刷新按钮，即可进行任务抽取，如果没有符合条件的任务，会显示“暂无选定任务”。初始是没有任务的，用户可以自行添加。

此外，点击首页下方的6个时间按钮也可以进行任务抽取，我将在添加任务部分具体介绍。

![首页](https://github.com/bingjiaqi123/MagicTimer-Android/assets/114491372/3df34b79-d546-4311-bccd-e7c913704362)

### 添加任务

点击首页的添加按钮，即可进入添加任务界面。

![添加按钮](https://github.com/bingjiaqi123/MagicTimer-Android/assets/114491372/b80821b7-7653-4e45-9add-4a4f450eea10)

用户输入任务名称、任务详情，选择任务时长、可执行次数，勾选适用情境（可多选），点击确定按钮即可添加任务到本地的form_data.txt文件和form_rule.txt文件中，支持连续提交任务。

![添加页面](https://github.com/bingjiaqi123/MagicTimer-Android/assets/114491372/f1c7d69a-0ab9-41f7-b698-6892b47d2895)

### 查看当前任务信息/编辑/删除当前任务

点击首页的编辑按钮，即可进入编辑任务界面。

![编辑按钮](https://github.com/bingjiaqi123/MagicTimer-Android/assets/114491372/404dd4d1-2e5f-46de-a731-dfe2e6e2a479)

文本框里会显示当前的任务信息。编辑任务界面采用和添加任务界面相同的表单，修改数据后点击更新按钮，即可更新当前的任务，支持对任务名称、任务详情、任务时长、可执行次数。适用情境的编辑；

点击删除按钮即可从form_data.txt文件和form_rule.txt文件中删除此任务，抽签状态回归“暂无选中任务”。

![编辑页面](https://github.com/bingjiaqi123/MagicTimer-Android/assets/114491372/2928a6c2-c458-4f1d-840f-f58ca06af537)

### 情境设置

点击首页的设置按钮，进入设置界面。

![设置按钮](https://github.com/bingjiaqi123/MagicTimer-Android/assets/114491372/cdf489d3-57b8-4f44-a135-f36dbe53622e)

点击情境设置按钮，进入情境设置界面。情境设置页面一共有8个待选项，分别是需要校园网、可断网、不限外放、通勤、户外运动、室内运动、独处思考、娱乐。

## 创意来源
做这个app的初衷是作为一名INTJ型人，经常处于以下两种状态中：

1-今天的网站有点卡呀，感觉还要十来秒才能加载出来的样子，看一下B站关注的up有没有更新视频吧→2000 years later，我是为什么打开这个网站来着？

2-今天整理下电脑，这不是我领取的备考资料吗？这不是我买的全套电子书吗？这不是我收藏的课程资源吗？这不是我下载的ps滤镜吗？（开心）从哪个开始学起呢？→看看日程表，论文写完了吗？推送做好了吗？英语刷题了吗？→没有，滚去做题→收藏的各种课外资料继续吃灰……

有人说，把一堆人关进一个屋子里什么也不让做，立刻开始给自己找事情干的一定是tj型人，哪有什么排队、等电梯、通勤的时间，tj型人闲下来一秒就觉得在浪费生命。可是说起对碎片时间的利用率，XNTJ型人又显然比不上XSTJ型人，只因为我们的爱好就像后宫佳丽三千，只有被冷落的，没有被割舍的。

意识到这两点后我开始寻找既能够更好地利用碎片时间，又能有效地对各种兴趣爱好“雨露均沾”的方法，前者基本都是柳比歇夫时间管理法的变体，经过我测评比较好的是时间日志app，但即使我买了终身会员，依然从没有完整地坚持过一天；后者则让我锁定了小决定app等转盘、抽签类app，但是选项一多转盘就难看，而且也只显示一个任务名，也不支持任务的详情编辑，还有十几秒的过场动画。比较好的结合了两者的是我现在用的时光伴侣app，可以一键切换到其他任务自动计时，并且允许记录中断原因，界面也很简洁。但对我来说，凡是需要手动选任务的，用久了就都是鸡肋，以我广泛的兴趣爱好，往往不到一个星期我就需要看着漫长的滚动条选任务了。

所以最终我还是只能自力更生，这个app的雏形很简单，就是类似于“今天吃什么？”的抽签器，但是仅仅是把几百条任务放在列表里一次抽完也是不够的，因为会有大量的无效任务。比如，我只有两三分钟的时间排队，连看一篇完整的剧评推文的时间都不够，适合我的是英语单词背3个、蚂蚁森林偷能量这种任务；而我在地铁上网络不好、身边嘈杂，即使戴上耳机也绝不适合看哔哩哔哩的视频。所以按任务需要的单位时长、任务适用的场景这两大筛选条件是绕不开的。最后，在任务编辑方面，我本来也想做个任务列表型页面，支持按任务名称和任务详情检索，支持按时长、可执行次数、适用情境筛选，奈何才疏学浅，实在是做不出来，我退而转念一想，与其花大量的时间去管理几百条任务，不如抽到哪个编辑哪个，更符号app的宗旨，而且即使出现了同名任务也不会报错。
