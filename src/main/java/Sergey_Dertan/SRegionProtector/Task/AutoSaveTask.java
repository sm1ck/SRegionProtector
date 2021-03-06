package Sergey_Dertan.SRegionProtector.Task;

import Sergey_Dertan.SRegionProtector.Main.SRegionProtectorMain;
import Sergey_Dertan.SRegionProtector.Main.SaveType;
import cn.nukkit.scheduler.AsyncTask;

public final class AutoSaveTask extends AsyncTask {

    private SRegionProtectorMain pl;

    public AutoSaveTask(SRegionProtectorMain pl) {
        this.pl = pl;
    }

    @Override
    public void onRun() {
        pl.save(SaveType.AUTO);
    }
}
