const mongoose = require('mongoose');

const Distributors = new mongoose.Schema({
    name: {
        type: String
    }
}, {
    timestamps: true
})
module.exports = mongoose.model('distributors', Distributors)