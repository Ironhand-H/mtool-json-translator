import {projectExist} from "../API/API.js";

function HomePage({changePage, selectedFileDir, handleChooseFile}) {
    const hasSelectedFile = (selectedFileDir !== "");

     async function handleContinue(){
        const response = await projectExist();

        if (response.data !== null){
            console.log(response);
            changePage("translate");
        }else{
            alert("No project found")
        }
    }

    return (
        <>
            <h1>AI-Translation-Toolkit</h1>
            <p>Welcome!</p>

            <button onClick={handleChooseFile}>
                Press to select file!
            </button>

            <button onClick={handleContinue}>
                A translate project is already in progress? Click here to continue
            </button>

            { hasSelectedFile && (
                <>
                    <p>Selected file: {selectedFileDir}</p>

                    <button onClick={() => changePage("translate")}>
                        File ready? Press to translate!
                    </button>
                </>
            )}

        </>
    )
}

export default HomePage