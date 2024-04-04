const mongoose = require('mongoose');
const local = "mongodb+srv://yenph:kmyhatcjkB@atlascluster.ayap5c0.mongodb.net/restapi?retryWrites=true&w=majority";
const connect = async () => {
    try {
        await mongoose.connect(local);
        console.log('Connect success');
    } catch (error) {
        console.error('Connection to MongoDB failed:', error);
    }
}
module.exports = {connect};