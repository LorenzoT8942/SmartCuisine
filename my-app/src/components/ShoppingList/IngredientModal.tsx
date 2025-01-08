import React, { useState } from 'react';
import axios from 'axios';
import '../../CSS/ingredient-modal.css';

interface Ingredient {
    id: number;
    name: String;
    quantity: number;
    unit: String;
}

interface IngredientModalProps {
    onClose: () => void;
    onAdd: (ingredient: Ingredient) => void;
}

const IngredientModal: React.FC<IngredientModalProps> = ({ onClose, onAdd }) => {
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResults, setSearchResults] = useState<Ingredient[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSearch = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get(`http://localhost:3004/ingredients?search=${searchTerm}`);
            setSearchResults(response.data);
        } catch (err) {
            setError('Error fetching ingredients');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="close-button" onClick={onClose}>X</button>
                <h2>Search Ingredient</h2>
                <form onSubmit={handleSearch}>
                    <input
                        type="text"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        placeholder="Search for an ingredient"
                    />
                    <button type="submit">Search</button>
                </form>
                {loading && <p>Loading...</p>}
                {error && <p>{error}</p>}
                <ul>
                    {searchResults.map((ingredient) => (
                        <li key={ingredient.id} onClick={() => onAdd(ingredient)}>
                            {ingredient.name}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default IngredientModal;