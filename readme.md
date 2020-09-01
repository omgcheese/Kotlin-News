# Kotlin News
Simple app for browsing r/kotlin

## Requirement
* Retrieve data from https://www.reddit.com/r/kotlin/.json
* The app must have two views: MainView and ArticleView
* MainView must be a list of reddit post from r/kotlin
* If the post does not contain an image, it should just display article title
* If the post does contain an image, display the image with proper aspect ratio and title should be
place on the top of the image.
* MainView must have a title as "Kotlin News"
* Article view must show thumbnail image at the top of the article
* If a post does not have a thumbnail image it should only show article body
* The app must run on various device
* The minimum SDK must be 23
* This codebase must be hosted in Github for evaluation

## Technical Research
### Example of data from r/kotlin/.json?limit=1
```json
{
    "kind": "Listing",
    "data": {
        "modhash": "",
        "dist": 1,
        "children": [
            {
                "kind": "t3",
                "data": {
                    "approved_at_utc": null,
                    "subreddit": "Kotlin",
                    "selftext": "",
                    "author_fullname": "t2_obpis",
                    "saved": false,
                    "mod_reason_title": null,
                    "gilded": 0,
                    "clicked": false,
                    "title": "Kotlin Multiplatform Mobile goes Alpha – Kotlin Blog",
                    "link_flair_richtext": [],
                    "subreddit_name_prefixed": "r/Kotlin",
                    "hidden": false,
                    "pwls": 6,
                    "link_flair_css_class": null,
                    "downs": 0,
                    "top_awarded_type": null,
                    "hide_score": false,
                    "name": "t3_ik1x3g",
                    "quarantine": false,
                    "link_flair_text_color": "dark",
                    "upvote_ratio": 0.96,
                    "author_flair_background_color": null,
                    "subreddit_type": "public",
                    "ups": 70,
                    "total_awards_received": 0,
                    "media_embed": {},
                    "author_flair_template_id": null,
                    "is_original_content": false,
                    "user_reports": [],
                    "secure_media": null,
                    "is_reddit_media_domain": false,
                    "is_meta": false,
                    "category": null,
                    "secure_media_embed": {},
                    "link_flair_text": null,
                    "can_mod_post": false,
                    "score": 70,
                    "approved_by": null,
                    "author_premium": false,
                    "thumbnail": "",
                    "edited": false,
                    "author_flair_css_class": null,
                    "author_flair_richtext": [],
                    "gildings": {},
                    "content_categories": null,
                    "is_self": false,
                    "mod_note": null,
                    "created": 1598922254,
                    "link_flair_type": "text",
                    "wls": 6,
                    "removed_by_category": null,
                    "banned_by": null,
                    "author_flair_type": "text",
                    "domain": "blog.jetbrains.com",
                    "allow_live_comments": false,
                    "selftext_html": null,
                    "likes": null,
                    "suggested_sort": null,
                    "banned_at_utc": null,
                    "url_overridden_by_dest": "https://blog.jetbrains.com/kotlin/2020/08/kotlin-multiplatform-mobile-goes-alpha/",
                    "view_count": null,
                    "archived": false,
                    "no_follow": false,
                    "is_crosspostable": false,
                    "pinned": false,
                    "over_18": false,
                    "all_awardings": [],
                    "awarders": [],
                    "media_only": false,
                    "can_gild": false,
                    "spoiler": false,
                    "locked": false,
                    "author_flair_text": null,
                    "treatment_tags": [],
                    "visited": false,
                    "removed_by": null,
                    "num_reports": null,
                    "distinguished": null,
                    "subreddit_id": "t5_2so2r",
                    "mod_reason_by": null,
                    "removal_reason": null,
                    "link_flair_background_color": "",
                    "id": "ik1x3g",
                    "is_robot_indexable": true,
                    "report_reasons": null,
                    "author": "dayanruben",
                    "discussion_type": null,
                    "num_comments": 1,
                    "send_replies": true,
                    "whitelist_status": "all_ads",
                    "contest_mode": false,
                    "mod_reports": [],
                    "author_patreon_flair": false,
                    "author_flair_text_color": null,
                    "permalink": "/r/Kotlin/comments/ik1x3g/kotlin_multiplatform_mobile_goes_alpha_kotlin_blog/",
                    "parent_whitelist_status": "all_ads",
                    "stickied": false,
                    "url": "https://blog.jetbrains.com/kotlin/2020/08/kotlin-multiplatform-mobile-goes-alpha/",
                    "subreddit_subscribers": 37685,
                    "created_utc": 1598893454,
                    "num_crossposts": 0,
                    "media": null,
                    "is_video": false
                }
            }
        ],
        "after": "t3_ik1x3g",
        "before": null
    }
}
```
It looks like there are various common parameter that is available to use:
* Limit - Cutting the limit of number of data to indicated number
* Before - To view pages before the current page. If you are fetching the most recent news, before would be null.
* After - to view pages after the current page.

After inspecting these attributes, infinite scrolling would only require to use `After` query param because there
is no reason to go back we would always have previous data.

An attribute called `url_overridden_by_dest` is responsible for having external source where photo items can be retrieved.
This attribute can be image (.jpg, .png, or normal external links) so we would need some kind of parser to determine
if the url is indeed an image.

An attribute called `selftext` is responsible for the body of an article. We can use this in the article view. I can see `\n`
are part of the content of this attribute so it should not render the actual escaping characters.

An attribute called `id` is the id of the post. We can use this for diffUtil and caching if needed.

### Library to use
* Koin for dependency injection - For such small app, Dagger would be a overkill
* Coroutine for async operations and thread management
* Retrofit for networking framework
* Moshi for light-weight serialization/deserialization json
* Android Jetpack libraries (ViewModel, LiveData, Navigation) for Google's MVVM design pattern recommendation
* Cardview, RecyclerView for UI requirement
* jUnit for unit test
* If there is time, robolectric for instrumental unit tests

### Architecture path
* Aiming for Clean Architecture with MVVM design pattern for testable codebase
* Even though this is a simple app, multi-modules can help scale the app immensely
* Repository pattern. If there is time, caching article with Room may be a nice touch
* Making sure any business logics are tested.

### Pre-merge strategies
* Use circleCi to do pre-merge check for each pull request.
* For develop or master branch, circleci can store artifacts for 30 days.
* No time or resource for slack notification or QR code.

### Error path
* All Api error handling must be implemented
* All Api request must be wrapped with Resource class (Success or Failure)
* User should be aware of network issue
* If error was occurred before the first batch was loaded, it should have an option to re-fetch the list again
