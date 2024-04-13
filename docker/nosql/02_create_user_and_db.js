db = db.getSiblingDB("admin");
db.auth("root", "root");
db = db.getSiblingDB("webcrawlerclassifier");
db.createUser({
  user: "admin",
  pwd: "admin",
  roles: [
    {
      role: "readWrite",
      db: "admin",
    },
  ],
});
