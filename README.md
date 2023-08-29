# MagicTimer-Android
==一些写在前面的碎碎念==
第一次用Android Studio做出像样的app，虽然UI还是很惨不忍睹(╥╯^╰╥)，但至少蛮简洁的。
开始做的时候本来想做秒表、倒计时、专注奖励、自动生成海报、周分析月分析……实际做起来发现一个情境设置功能就要了我半条命，另一条命交代给了编辑功能（虽然编辑功能还是很简陋的），但最终总算还是实现了按情境筛选任务、按任务时长抽签、编辑/删除当前任务3个刚需的功能，而且只用了4个txt文件管理所有任务和设置，在这个过程里还学会了用python画出了现在的app图标（要是我的UI也能像任务图标一样可爱就好了QAQ）。
现在的功能编写基本都是我日夜督促ChatGPT，与其斗智斗勇完成的，短时间内应该不会大改，下一步的计划是希望能把“可执行次数”这行数据利用起来。唯一（？）的小缺憾就是虽然我成功禁用了顶部的返回按钮，但顶部标签栏就是无法删除或隐藏，如果有大佬解决了这个问题真的万分感谢！
## 创意来源
做这个app的初衷是作为一名INTJ型人，经常处于以下两种状态中：
1-今天的网站有点卡呀，感觉还要十来秒才能加载出来的样子，看一下B站关注的up有没有更新视频吧→2000 years later，我是为什么打开这个网站来着？
2-今天整理下电脑，这不是我领取的备考资料吗？这不是我买的全套电子书吗？这不是我收藏的课程资源吗？这不是我下载的ps滤镜吗？（开心）从哪个开始学起呢？→看看日程表，论文写完了吗？推送做好了吗？英语刷题了吗？→没有，滚去做题→收藏的各种课外资料继续吃灰……
有人说，把一堆人关进一个屋子里什么也不让做，立刻开始给自己找事情干的一定是tj型人，哪有什么排队、等电梯、通勤的时间，tj型人闲下来一秒就觉得在浪费生命。可是说起对碎片时间的利用率，XNTJ型人又显然比不上XSTJ型人，只因为我们的爱好就像后宫佳丽三千，只有被冷落的，没有被割舍的。
意识到这两点后我开始寻找既能够更好地利用碎片时间，又能有效地对各种兴趣爱好“雨露均沾”的方法，前者基本都是柳比歇夫时间管理法的变体，经过我测评比较好的是时间日志app，但即使我买了终身会员，依然从没有完整地坚持过一天；后者则让我锁定了小决定app等转盘、抽签类app，但是选项一多转盘就难看，而且也只显示一个任务名，也不支持任务的详情编辑，还有十几秒的过场动画。比较好的结合了两者的是我现在用的时光伴侣app，可以一键切换到其他任务自动计时，并且允许记录中断原因，界面也很简洁。但对我来说，凡是需要手动选任务的，用久了就都是鸡肋，以我广泛的兴趣爱好，往往不到一个星期我就需要看着漫长的滚动条选任务了。
所以最终我还是只能自力更生，这个app的雏形很简单，就是类似于“今天吃什么？”的抽签器，但是仅仅是把几百条任务放在列表里一次抽完也是不够的，因为会有大量的无效任务。比如，我只有两三分钟的时间排队，连看一篇完整的剧评推文的时间都不够，适合我的是英语单词背3个、蚂蚁森林偷能量这种任务；而我在地铁上网络不好、身边嘈杂，即使戴上耳机也绝不适合看哔哩哔哩的视频。所以按任务需要的单位时长、任务适用的场景这两大筛选条件是绕不开的。最后，在任务编辑方面，我本来也想做个任务列表型页面，支持按任务名称和任务详情检索，支持按时长、可执行次数、适用情境筛选，奈何才疏学浅，实在是做不出来，我退而转念一想，与其花大量的时间去管理几百条任务，不如抽到哪个编辑哪个，更符号app的宗旨，而且即使出现了同名任务也不会报错。
