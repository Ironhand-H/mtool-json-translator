const { app, BrowserWindow } = require("electron");
const { spawn } = require("child_process");
const path = require("path");

let backendProcess;
let mainWindow;

/**
 * 根据运行环境确定 Java 和 Jar 的位置
 */
const javaExecutable = app.isPackaged
    ? path.join(process.resourcesPath, "runtime", "bin", "java.exe")
    : "java";

const jarFile = app.isPackaged
    ? path.join(process.resourcesPath, "backend", "app.jar")
    : path.join(
        __dirname,
        "target",
        "mtool-json-translator-0.0.1-SNAPSHOT.jar"
    );

/**
 * 等待指定时间
 */
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

/**
 * 启动 Spring Boot
 */
function startBackend() {

    console.log("Java:", javaExecutable);
    console.log("Jar :", jarFile);

    backendProcess = spawn(
        javaExecutable,
        [
            "-jar",
            jarFile
        ],
        {
            cwd: app.isPackaged
                ? process.resourcesPath
                : __dirname
        }
    );

    backendProcess.stdout.on("data", data => {
        console.log("[Spring]", data.toString());
    });

    backendProcess.stderr.on("data", data => {
        console.error("[Spring Error]", data.toString());
    });

    backendProcess.on("close", code => {
        console.log(`Spring Boot exited. Code: ${code}`);
    });

    backendProcess.on("error", err => {
        console.error("[Spawn Error]", err);
    });
}

/**
 * 等待 Spring Boot 启动完成
 */
async function waitBackend() {

    while (true) {

        try {

            const response = await fetch("http://localhost:8080");

            if (response.ok) {
                console.log("Spring Boot started.");
                return;
            }

        } catch (e) {
            // Spring Boot 尚未启动
        }

        await sleep(500);
    }
}

/**
 * 创建 Electron 窗口
 */
function createWindow() {

    mainWindow = new BrowserWindow({

        width: 1200,
        height: 800,

        webPreferences: {
            contextIsolation: true
        }

    });

    mainWindow.loadURL("http://localhost:8080");

    mainWindow.on("closed", () => {
        mainWindow = null;
    });
}

/**
 * Electron 启动
 */
app.whenReady().then(async () => {

    console.log("Starting Spring Boot...");

    startBackend();

    await waitBackend();

    createWindow();

});

/**
 * 所有窗口关闭
 */
app.on("window-all-closed", () => {

    console.log("Closing application...");

    if (backendProcess) {
        backendProcess.kill();
    }

    app.quit();

});

/**
 * macOS 点击 Dock 图标重新打开窗口
 */
app.on("activate", () => {

    if (BrowserWindow.getAllWindows().length === 0) {
        createWindow();
    }

});