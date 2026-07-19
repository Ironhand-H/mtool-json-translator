function HomePage({changePage, selectedFileDir, handleChooseFile}) {
    const hasSelectedFile = (selectedFileDir !== "");

    return (
        <>
            <h1>AI-Translation-Toolkit</h1>
            <p>Welcome!</p>

            <button onClick={handleChooseFile}>
                Press to select file!
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