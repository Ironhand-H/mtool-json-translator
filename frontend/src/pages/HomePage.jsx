import {chooseFile} from "../API/API.js";
import {useState} from "react";

function HomePage({changePage}) {
    const [fileDir, setFileDir] = useState("");

    async function handleChooseFile() {

        const result = await chooseFile();

        //console.log(result);

        setFileDir(result.data.fileDir);
    }

    return (
        <>
            <h1>AI-Translation-Toolkit</h1>
            <p>Welcome!</p>

            <button onClick={() => changePage("translate")}>
                Press to translate!
            </button>
            <button onClick={handleChooseFile}>
                Press to select file!
            </button>
            <p>{fileDir}</p>
        </>
    )
}

export default HomePage