function HomePage({changePage}) {
    return (
        <>
            <h1>AI-Translation-Toolkit</h1>
            <p>Welcome!</p>

            <button onClick={() => changePage("translate")}>
                Press to translate!
            </button>
            <button onClick={() => fetch("/api/chooseFile", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    "path": "jsonfile"
                })
            })}>
                Press to select file!
            </button>
        </>
    )
}

export default HomePage