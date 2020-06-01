#curl -XPUT --header 'Content-Type: application/json' "localhost:9200/blog/post/1?pretty" -d'
#{
#  "title": "Веселые котята",
#  "content": "<p>Смешная история про котят<p>",
#  "tags": [
#    "котята",
#    "смешная история"
#  ],
#  "published_at": "2014-09-12T20:44:42+00:00"
#}'
curl -XGET --header 'Content-Type: application/json' http://localhost:9200/blog/post/_search -d '{
      "query" : {
        "match" : { "title": "Веселые" }
    }
}'
