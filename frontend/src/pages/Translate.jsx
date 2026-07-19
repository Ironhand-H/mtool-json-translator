import {createProject, projectExist} from "../API/API.js";
import {useEffect, useState} from "react";

function Translate({selectedFileDir}) {
    const [projectInfo, setProjectInfo] = useState(null);
    const [hasProjectCreated, setHasProjectCreated] = useState(false);

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

    useEffect(() => {
        refresh();
    }, []);



    return (
        <>
            <h1>Translate Page</h1>
            <p>Selected File: {selectedFileDir}</p>

            {hasProjectCreated && (
                <>
                    <p>Info: {projectInfo}</p>
                </>
            )}
            {!hasProjectCreated && (
                <>
                    <p>You need to create a project before start! Create now?</p>
                    <button onClick={handleCreateProject}>Create project</button>
                </>
            )}
        </>
    )
}

export default Translate;