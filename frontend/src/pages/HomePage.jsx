import {chooseFile} from "../API/API.js";
import {useState} from "react";

function HomePage({changePage}) {
    const [fileDir, setFileDir] = useState("");
    const hasSelectedFile = (fileDir !== "");

    async function handleChooseFile() {

        const result = await chooseFile();

        //console.log(result);

        if(result.success){
            setFileDir(result.data.fileDir);
        }
    }

    return (
        <>
            <h1>AI-Translation-Toolkit</h1>
            <p>Welcome!</p>

            <button onClick={handleChooseFile}>
                Press to select file!
            </button>


            { hasSelectedFile && (
                <>
                    <p>Selected file: {fileDir}</p>

                    <button onClick={() => changePage("translate")}>
                        File ready? Press to translate!
                    </button>
                </>
            )}


        </>
    )
}

export default HomePage