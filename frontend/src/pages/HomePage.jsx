function HomePage({changePage}) {
    return (
        <>
            <h1>AI-Translation-Toolkit</h1>
            <p>Welcome!</p>

            <button onClick={() => changePage("translate")}>
                Press to translate!
            </button>
        </>
    )
}

export default HomePage