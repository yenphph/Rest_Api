const mongoose = require('mongoose');

const FruitSchema = new mongoose.Schema({
    name: {
        type: String
    },
    quantity: {
        type: Number,
    },
    price: {
        type: Number,
    },
    status: {
        type: String
    },
    id_distributor: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'distributors'//khoa ngoai
    }
}, {
    timestamps: true
});

module.exports = mongoose.model('fruit', FruitSchema);