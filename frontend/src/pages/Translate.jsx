import {createProject, projectExist, startTask} from "../API/API.js";
import {useEffect, useState} from "react";

function Translate({selectedFileDir}) {
    const [projectInfo, setProjectInfo] = useState(null);
    const [hasProjectCreated, setHasProjectCreated] = useState(false);
    const [taskIsRunning, setTaskIsRunning] = useState(false);
    const [modelName, setModelName] = useState("qwen/qwen3-14b");
    const [url, setUrl] = useState("http://127.0.0.1:1234/v1/chat/completions");

    async function refresh() {
        const result = await projectExist();

        if(result.success && result.data !== null){
            setProjectInfo(result.data);
            setHasProjectCreated(true);
        }
    }

    async function handleCreateProject() {
        const result = await createProject(selectedFileDir);
        console.log(result);

        if(result.success && result.data !== null){
            setProjectInfo(result.data);
            setHasProjectCreated(true);
        }
    }

    async function handleStartTask(){
        console.log("1");
        const result = await startTask(modelName, url);
        console.log(result);

        if(result.success){
            setTaskIsRunning(true);
        }
    }

    useEffect(() => {
        refresh();

        const timer = setInterval(() => {
            refresh();
        }, 2000);

        return () => clearInterval(timer);
    }, []);



    return (
        <>
            <h1>Translate Page</h1>

            <input name={"modelName"} defaultValue={modelName}
                   onChange={(e) => setModelName(e.target.value)}
            />
            <input name={"url"} defaultValue={url}
                   onChange={(e) => setUrl(e.target.value)}
            />
            {hasProjectCreated && (
                <>
                    <p>Current project status: {taskIsRunning && (" translating")}</p>
                    <p>Progress: {projectInfo.currentBatch}</p>
                    <p>Total: {projectInfo.totalBatch}</p>

                    <button disabled={taskIsRunning} onClick={() => {
                        console.log("clicked");
                        handleStartTask();
                    }}>
                        Start translation
                    </button>
                </>
            )}
            {!hasProjectCreated && (
                <>
                    <p>You need to create a project before start! Create now?</p>
                    <button onClick={handleCreateProject}>
                        Create project
                    </button>
                </>
            )}
        </>
    )
}

export default Translate;