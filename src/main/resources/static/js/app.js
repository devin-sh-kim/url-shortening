String.prototype.truncate = function(m) {
    return (this.length > m)
        // ? jQuery.trim(this).substring(0, m).split(" ").slice(0, -1).join(" ") + "..."
        ? jQuery.trim(this).substring(0, m) + "..."
        : this;
};