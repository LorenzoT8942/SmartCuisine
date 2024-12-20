import React, { useState } from "react";

const SearchBar = ({ onSearch }) => {
  const [query, setQuery] = useState("");

  const handleKeyDown = async (e) => {
    if (e.key === "Enter") {
      try {
        // Call the search handler passed as a prop
        await onSearch(query);
      } catch (error) {
        console.error("Error during search:", error);
      }
    }
  };

  return (
    <div className="search-bar">
      <input
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        onKeyDown={handleKeyDown}
        placeholder="Search for recipes..."
        className="search-bar__input"
      />
    </div>
  );
};

export default SearchBar;
