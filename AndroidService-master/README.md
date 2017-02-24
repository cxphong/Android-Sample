# LearnService用于学习如何去使用两种方式去启动service
服务是一个应用程序组件，可以在后台执行长时间运行的操作，不提供用户界面。一个应用程序组件可以启动一个服务，它将继续在后台运行，即使用户切换到另一个应用程序。此外，一个组件可以绑定到一个服务与它交互，甚至执行进程间通信(IPC)。例如，一个服务可能处理网络通信、播放音乐、计时操作或与一个内容提供者交互，都在后台执行。
1.startService/stopService
2.bindService/unBindService
详情请见：http://www.jianshu.com/p/5505390503fa